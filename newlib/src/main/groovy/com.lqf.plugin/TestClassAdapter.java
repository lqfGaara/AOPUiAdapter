package com.lqf.plugin;

import com.lqf.plugin.utils.FilterUtil;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

public  class TestClassAdapter extends ClassVisitor {
    public TestClassAdapter(ClassVisitor cv) {
        super(Opcodes.ASM5,cv);
    }
    @Override
    public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
        MethodVisitor methodVisitor=super.visitMethod(access, name, desc, signature, exceptions);
        if (FilterUtil.isMatchingMethod(name,desc)){
            return methodVisitor==null?null:new TestMethodAdapter(methodVisitor);
        }else {
            return methodVisitor;
        }
    }
}
