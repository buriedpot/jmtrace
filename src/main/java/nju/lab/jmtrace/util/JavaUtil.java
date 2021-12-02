package nju.lab.jmtrace.util;

public class JavaUtil {
    public static boolean isJavaLibraryClass(String className) {
        if (className == null) return true;
        return className.startsWith("java/") ||
                className.startsWith("sun/") ||
                className.startsWith("javax/") ||
                className.startsWith("com/sun/") ||
                className.startsWith("org/omg/") ||
                className.startsWith("org/xml/") ||
                className.startsWith("org/w3c/dom/") ||
                className.startsWith("jdk/");
    }

    /**
     * category 1: 1slot, 4byte
     * category 2: 2slot, 8byte
     * @param descriptor
     * @return
     */
    public static int category(String descriptor) {
        if (descriptor == null) return 0;
        char startChar = descriptor.charAt(0);
        switch (startChar) {
            case 'B':
            case 'Z':
            case 'C':
            case 'S':
            case 'I':
            case 'F':
            case 'L':
            case '[':
                return 1;
            case 'D':
            case 'J':
                return 2;
            default:
                return -1;
        }
    }

    /**
     * e.g., [Ljava/lang/String  ->  java.lang.String
     * [[Ljava/lang/Strnig  ->  java.lang.String[]
     */
    public static String arrayDesc2Name(String descriptor) {
        if (descriptor == null || descriptor.length() <= 1) {
            return new String();
        }
        int dim = 0;
        //for (int i = 1; i < descriptor.size)
        // TODO
        return null;
    }
}
