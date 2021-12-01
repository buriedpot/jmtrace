package visitor;

import instrumentation.JMTraceInstrumentation;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import util.JavaUtil;

import static org.objectweb.asm.Opcodes.*;

public class JMTraceMethodVisitor extends MethodVisitor {
    public JMTraceMethodVisitor(MethodVisitor methodVisitor) {
        super(Opcodes.ASM7, methodVisitor);
    }

    @Override
    public void visitInsn(int opcode) {
        /**
         * Array element load
         */
        if (opcode >= IALOAD && opcode <= SALOAD) {
            mv.visitInsn(Opcodes.DUP2);
            mv.visitMethodInsn(Opcodes.INVOKESTATIC,
                    Type.getInternalName(JMTraceInstrumentation.class),
                    "instr_ALOAD",
                    "(Ljava/lang/Object;I)V",
                    false);
        }
        /**
         * Array element store
         */
        else if (opcode >= IASTORE && opcode <= SASTORE) {
            /**
             * stack: ..., value3, value2, value1, need to be transformed to
             * ..., value3, value2, value1, value3, value2
             * value3: 1byte, array reference
             * value2: 1byte, array index
             * value1: 2byte, long or double
             */
            if (opcode == LASTORE || opcode == DASTORE) {
                // ..., 3, 2, 1
                mv.visitInsn(DUP2_X2); //..., 1, 3, 2, 1
                mv.visitInsn(POP2);// ..., 1, 3, 2
                mv.visitInsn(DUP2_X2); //..., 3, 2, 1, 3, 2
            }
            /**
             * stack: ..., value3, value2, value1, need to be transformed to
             * ..., value3, value2, value1, value3, value2
             * value3: 1byte, array reference
             * value2: 1byte, array index
             * value1: 1byte
             */
            else {
                // ..., 3, 2, 1
                mv.visitInsn(DUP2_X1); //..., 2, 1, 3, 2, 1
                mv.visitInsn(POP2);// ..., 2, 1, 3
                mv.visitInsn(DUP2_X1); //..., 1, 3, 2, 1, 3
                mv.visitInsn(POP2);// ..., 1, 3, 2
                mv.visitInsn(DUP2_X1); // ..., 3, 2, 1, 3, 2
            }
            mv.visitMethodInsn(Opcodes.INVOKESTATIC,
                    Type.getInternalName(JMTraceInstrumentation.class),
                    "instr_ASTORE",
                    "(Ljava/lang/Object;I)V",
                    false);
        }
        mv.visitInsn(opcode);
    }

    @Override
    public void visitFieldInsn(int opcode, String owner, String name, String descriptor) {
        if (mv == null) {
            return;
        }

        if (JavaUtil.isJavaLibraryClass(owner)) {
            mv.visitFieldInsn(opcode, owner, name, descriptor);
            return;
        }
        switch (opcode) {
            case GETFIELD: {
                mv.visitInsn(Opcodes.DUP);
                mv.visitLdcInsn(owner);
                mv.visitLdcInsn(name);
                mv.visitMethodInsn(Opcodes.INVOKESTATIC,
                        Type.getInternalName(JMTraceInstrumentation.class),
                        "instrGetField",
                        "(Ljava/lang/Object;Ljava/lang/String;Ljava/lang/String;)V",
                        false);
                break;
            }
            case GETSTATIC: {
                mv.visitLdcInsn(owner);
                mv.visitLdcInsn(owner);
                mv.visitLdcInsn(name);
                mv.visitMethodInsn(Opcodes.INVOKESTATIC,
                        Type.getInternalName(JMTraceInstrumentation.class),
                        "instrGetStatic",
                        "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V",
                        false);
                break;
            }
            case PUTFIELD: {
                int category = JavaUtil.category(descriptor);
                if (category == 1) {
                    mv.visitInsn(Opcodes.DUP2);
                    mv.visitInsn(Opcodes.POP);
                }
                /**
                 * stack: ..., value2, value1. need to be transformed to ..., value2, value1, value2
                 * value2: category 1
                 * value1: category 2
                 */
                else if (category == 2) {
                    //..., 2, 1
                    mv.visitInsn(DUP2_X1);//..., value1, value2, value1
                    mv.visitInsn(POP2);//..., value1, value2
                    mv.visitInsn(DUP_X2);//..., value2, value1, value2,
                    mv.visitInsn(DUP_X2);//..., value2, value2, value1, value2
                    mv.visitInsn(POP);//..., value2, value2, value1
                    mv.visitInsn(DUP2_X1);//..., value2, value1, value2, value1
                    mv.visitInsn(POP2);//..., value2, value1, value2
                }
                else mv.visitLdcInsn(owner);
                mv.visitLdcInsn(owner);
                mv.visitLdcInsn(name);
                mv.visitMethodInsn(Opcodes.INVOKESTATIC,
                        Type.getInternalName(JMTraceInstrumentation.class),
                        "instrPutField",
                        "(Ljava/lang/Object;Ljava/lang/String;Ljava/lang/String;)V",
                        false);
                break;
            }
            case PUTSTATIC: {
                mv.visitLdcInsn(owner);
                mv.visitLdcInsn(owner);
                mv.visitLdcInsn(name);
                mv.visitMethodInsn(Opcodes.INVOKESTATIC,
                        Type.getInternalName(JMTraceInstrumentation.class),
                        "instrPutStatic",
                        "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V",
                        false);
                break;
            }
        }

        mv.visitFieldInsn(opcode, owner, name, descriptor);
    }
}
