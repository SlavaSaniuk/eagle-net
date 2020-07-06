package by.bsac.utilities;

/**
 * Additional class utilities.
 */
public final class ClazzUtils {

    private static Class[] primitive_wrappers_types = new Class[] {Byte.class, Short.class, Integer.class, Long.class, Float.class, Double.class, Character.class, Void.class};

    /**
     * Method determine if specified class parameter is wrapper class of primitive type.
     * @param clazz - class to determine
     * @return - true, if class is primitive wrapper type.
     */
    public static boolean isPrimitiveWrapper(Class clazz) {

        if (clazz == null) throw new NullPointerException("Class parameter is null");

        for (Class c : primitive_wrappers_types) if (c.equals(clazz)) return true;
        return false;
    }
}
