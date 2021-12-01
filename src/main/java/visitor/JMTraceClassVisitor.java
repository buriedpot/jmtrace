package visitor;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import util.JavaUtil;

public class JMTraceClassVisitor extends ClassVisitor {

    public JMTraceClassVisitor(int api, ClassVisitor writer) {
        super(api, writer);
    }

    public JMTraceClassVisitor(ClassVisitor writer) {
        super(Opcodes.ASM7, writer);
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
        MethodVisitor mv = cv.visitMethod(access, name, descriptor, signature, exceptions);
        return new JMTraceMethodVisitor(mv);
    }

}
