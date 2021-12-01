package nju.lab.jmtrace.visitor;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

public class JMTraceClassVisitor extends ClassVisitor {

    public JMTraceClassVisitor(ClassVisitor writer) {
        super(Opcodes.ASM7, writer);
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
        MethodVisitor mv = cv.visitMethod(access, name, descriptor, signature, exceptions);
        return new JMTraceMethodVisitor(mv);
    }

}
