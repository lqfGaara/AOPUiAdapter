package com.lqf.plugin;

import com.android.build.api.transform.DirectoryInput;
import com.android.build.api.transform.Format;
import com.android.build.api.transform.JarInput;
import com.android.build.api.transform.QualifiedContent;
import com.android.build.api.transform.Transform;
import com.android.build.api.transform.TransformException;
import com.android.build.api.transform.TransformInput;
import com.android.build.api.transform.TransformInvocation;
import com.android.build.api.transform.TransformOutputProvider;
import com.android.build.gradle.internal.pipeline.TransformManager;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.gradle.api.Project;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.jar.JarOutputStream;
import java.util.zip.ZipEntry;

import static org.objectweb.asm.ClassReader.EXPAND_FRAMES;

public class MyTransform extends Transform {
    Project project;
    private boolean isIncremental;

    public MyTransform(Project project) {
            this.project=project;
    }

    @Override
    public void transform(TransformInvocation transformInvocation) throws TransformException, InterruptedException, IOException {
       System.out.println("--------------- LifecyclePlugin visit start --------------- ");
        long startTime = System.currentTimeMillis();
        Collection<TransformInput> inputs = transformInvocation.getInputs();
        TransformOutputProvider outputProvider = transformInvocation.getOutputProvider();
        //删除之前的输出
        if (outputProvider != null)
            outputProvider.deleteAll();
        //遍历inputs
        for(TransformInput input  :inputs){
            for (DirectoryInput directoryInput:input.getDirectoryInputs()){
                handleDirectoryInput(directoryInput,outputProvider);
            }
            for (JarInput jarInput:input.getJarInputs()){
                handleJarInputs(jarInput,outputProvider);
            }
        }
        double cost = (System.currentTimeMillis() - startTime) / 1000;
        System.out.println("--------------- LifecyclePlugin visit end --------------- ");
        System.out.println("耗时"+cost+"秒");
    }
    @Override
    public String getName() {
        return "newlib";
    }

    @Override
    public Set<QualifiedContent.ContentType> getInputTypes() {
        return TransformManager.CONTENT_CLASS;
    }

    @Override
    public Set<? super QualifiedContent.Scope> getScopes() {
        return TransformManager.SCOPE_FULL_PROJECT;
    }

    @Override
    public boolean isIncremental() {
        return isIncremental;
    }
    /**
     * 检查class文件是否需要处理
     * @return
     */
    static boolean checkClassFile(String name) {
        //只处理需要的class文件
        return (name.endsWith(".class") && !name.startsWith("R\\$")
                && !"R.class".equals(name) && !"BuildConfig.class".equals(name)
                && "android/support/v7/app/AppCompatActivity.class".equals(name));
    }
    /**
     * 处理文件目录下的class文件
     */
    static void handleDirectoryInput(DirectoryInput directoryInput, TransformOutputProvider outputProvider) throws IOException {
        //是否是目录
        System.out.println("----------- deal with class file < + ");
        if (directoryInput.getFile().isDirectory()) {
            //列出目录所有文件（包含子文件夹，子文件夹内文件）
            for (File file:directoryInput.getFile().listFiles()){
                System.out.println("----------- deal with class file <'"+ file.getName() + "");
                if (checkClassFile(file.getName())){
                    FileInputStream  fis =new FileInputStream(file);
                    ClassReader classReader = new ClassReader(IOUtils.toByteArray(fis));
                    ClassWriter classWriter = new ClassWriter(classReader, ClassWriter.COMPUTE_MAXS);
                    ClassVisitor cv = new TestClassAdapter(classWriter);
                    classReader.accept(cv, EXPAND_FRAMES);
                    byte[] code = classWriter.toByteArray();
                    FileOutputStream fos = new FileOutputStream(file.getParentFile().getAbsolutePath() + File.separator + file.getName());
                    fos.write(code);
                    fos.close();
                }
            }
        }
        //处理完输入文件之后，要把输出给下一个任务
        File dest = outputProvider.getContentLocation(directoryInput.getName(),
                directoryInput.getContentTypes(), directoryInput.getScopes(),
                Format.DIRECTORY);
        org.apache.commons.io.FileUtils.copyDirectory(directoryInput.getFile(), dest);
    }
    /**
     * 处理Jar中的class文件
     */
    static void handleJarInputs(JarInput jarInput, TransformOutputProvider outputProvider) throws IOException {
        if (jarInput.getFile().getAbsolutePath().endsWith(".jar")) {
            //重名名输出文件,因为可能同名,会覆盖
            String jarName = jarInput.getName();
            String md5Name = DigestUtils.md5Hex(jarInput.getFile().getAbsolutePath());
            if (jarName.endsWith(".jar")) {
                jarName = jarName.substring(0, jarName.length() - 4);
            }
            JarFile jarFile = new JarFile(jarInput.getFile());
            Enumeration enumeration = jarFile.entries();
            File tmpFile = new File(jarInput.getFile().getParent() + File.separator + "classes_temp.jar");
            //避免上次的缓存被重复插入
            if (tmpFile.exists()) {
                tmpFile.delete();
            }
            JarOutputStream jarOutputStream = new JarOutputStream(new FileOutputStream(tmpFile));
            //用于保存
            while (enumeration.hasMoreElements()) {
                JarEntry jarEntry = (JarEntry) enumeration.nextElement();
                String entryName = jarEntry.getName();
                ZipEntry zipEntry = new ZipEntry(entryName);
                InputStream inputStream = jarFile.getInputStream(jarEntry);
                //插桩class
                if (checkClassFile(entryName)) {
                    //class文件处理
                    System.out.println("----------- deal with jar class file <" + entryName + ">");
                    jarOutputStream.putNextEntry(zipEntry);
                    ClassReader classReader = new ClassReader(IOUtils.toByteArray(inputStream));
                    ClassWriter classWriter = new ClassWriter(classReader, ClassWriter.COMPUTE_MAXS);
                    ClassVisitor cv = new TestClassAdapter(classWriter);
                    classReader.accept(cv, EXPAND_FRAMES);
                    byte[] code = classWriter.toByteArray();
                    jarOutputStream.write(code);
                } else {
                    jarOutputStream.putNextEntry(zipEntry);
                    jarOutputStream.write(IOUtils.toByteArray(inputStream));
                }
                jarOutputStream.closeEntry();
            }
            //结束
            jarOutputStream.close();
            jarFile.close();
            File dest = outputProvider.getContentLocation(jarName + md5Name,
                    jarInput.getContentTypes(), jarInput.getScopes(), Format.JAR);
            FileUtils.copyFile(tmpFile, dest);
            tmpFile.delete();
        }
    }
}
