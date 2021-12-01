package instrumentation;

public class JMTraceInstrumentation {
    /**
     * instrumentation for getfield
     * @param object, the receive object of get field
     * @param owner, the class of the receive object
     * @param name, the field name of the receive object
     */

    synchronized public static void instrGetField(Object object, String owner, String name) {
        System.out.print("R " + Thread.currentThread().getId() + " ");
        System.out.printf("%016x", System.identityHashCode(object));
        System.out.println(" " + owner + "." + name);
    }
    /**
     * instrumentation for getstatic
     * @param className, the class of the static call
     * @param owner, the class of the receive object
     * @param name, the field name of the receive object
     */
    synchronized public static void instrGetStatic(String className, String owner, String name) {
        System.out.print("R " + Thread.currentThread().getId() + " ");
        System.out.printf("%016x", System.identityHashCode(className));
        System.out.println(" " + owner + "." + name);
    }
    /**
     * instrumentation for putfield
     * @param object, the class of the static call
     * @param owner, the class of the receive object
     * @param name, the field name of the receive object
     */
    synchronized public static void instrPutField(Object object, String owner, String name) {
        System.out.print("W " + Thread.currentThread().getId() + " ");
        System.out.printf("%016x", System.identityHashCode(object));
        System.out.println(" " + owner + "." + name);
    }
    /**
     * instrumentation for putstatic
     * @param className, the class of the static call
     * @param owner, the class of the receive object
     * @param name, the field name of the receive object
     */
    synchronized public static void instrPutStatic(String className, String owner, String name) {
        System.out.print("W " + Thread.currentThread().getId() + " ");
        System.out.printf("%016x", System.identityHashCode(className));
        System.out.println(" " + owner + "." + name);
    }

    /**
     * instrumentation for *aload
     * @param array
     * @param index
     */
    synchronized public static void instr_ALOAD(Object array, int index) {
        System.out.print("R " + Thread.currentThread().getId() + " ");
        System.out.printf("%016x", System.identityHashCode(array));
        System.out.println(" " + array.getClass() + "[" + index + "]");
    }

    /**
     * instrumentation for *astore
     * @param array
     * @param index
     */
    synchronized public static void instr_ASTORE(Object array, int index) {
        System.out.print("W " + Thread.currentThread().getId() + " ");
        System.out.printf("%016x", System.identityHashCode(array));
        System.out.println(" " + array.getClass() + "[" + index + "]");
    }

}
