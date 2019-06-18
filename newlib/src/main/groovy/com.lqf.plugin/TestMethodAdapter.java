package com.lqf.plugin;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;


public class TestMethodAdapter extends MethodVisitor implements Opcodes {
    public TestMethodAdapter(MethodVisitor mv) {
        super(Opcodes.ASM5,mv);
    }

    @Override
    public void visitCode() {
        super.visitCode();
        mv.visitVarInsn(Opcodes.ALOAD, 0);
        mv.visitVarInsn(Opcodes.ALOAD, 0);
        mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "android/support/v4/app/FragmentActivity", "getApplication", "()Landroid/app/Application;", false);
        mv.visitIntInsn(Opcodes.SIPUSH, 360);
        mv.visitMethodInsn(Opcodes.INVOKESTATIC, "lqf/com/myutils2/ScreenUtils", "setCustomDebsity", "(Landroid/app/Activity;Landroid/app/Application;I)V", false);
        mv.visitEnd();
    }
}
