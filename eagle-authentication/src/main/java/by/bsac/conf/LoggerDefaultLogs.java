package by.bsac.conf;

/**
 * This class has has typed logs messages of log Spring beans management.
 */
public class LoggerDefaultLogs {

    /**
     * Logs for Spring initialization of configuration classes.
     */
    public static class INITIALIZATION {

        public static String initConfig(Class clazz) {
            final String INITIALIZE_CONFIGURATION = "Start to initialize [%s] configuration class.";
            return String.format(INITIALIZE_CONFIGURATION, clazz.getSimpleName());
        }
    }

    /**
     * Logs for Spring beans creation processes.
     */
    public static class CREATION {

        public static String beanCreationStart(Class bean_class) {
            final String CREATE_BEAN_START = "Start to create [%s] bean.";
            return String.format(CREATE_BEAN_START, bean_class.getCanonicalName());
        }

        public static String beanCreationFinish(Class bean_class) {
            final String CREATE_BEAN_FINISH = "Bean [%s] successfully was created.";
            return String.format(CREATE_BEAN_FINISH, bean_class.getCanonicalName());
        }

        public static String beanCreationStartForProfile(Class bean_class, String profile) {
            final String CREATE_BEAN_START = "Start to create [%s] bean for [%s] profile.";
            return String.format(CREATE_BEAN_START, bean_class.getCanonicalName(), profile);
        }

        public static String beanCreationFinishForProfile(Class bean_class, String profile) {
            final String CREATE_BEAN_START = "Bean [%s] for [%s] profile successfully was created.";
            return String.format(CREATE_BEAN_START, bean_class.getCanonicalName(), profile);
        }

        public static class FOR_TYPE {
            public static String beanCreationStartForProfile(Class bean_class, String profile) {
                final String CREATE_BEAN_START = "Start to create [%s] bean for [%s] profile.";
                return String.format(CREATE_BEAN_START, bean_class.getCanonicalName(), profile);
            }
        }

    }

    /**
     * Logs for Spring dependency management processes (manual).
     */
    public static class DEPENDENCY {

        public static String viaSetter(Class dependent, Class to) {
            final String DEPENDENCY_VIA_SETTER = "Set [%s] bean to [%s] bean via setter method.";
            return String.format(DEPENDENCY_VIA_SETTER, dependent.getCanonicalName(), to.getCanonicalName());
        }

        public static String viaConstructor(Class dependent, Class to) {
            final String DEPENDENCY_VIA_CONSTRUCTOR = "Set [%s] bean to [%s] bean via constructor.";
            return String.format(DEPENDENCY_VIA_CONSTRUCTOR, dependent.getCanonicalName(), to.getCanonicalName());
        }

    }

    /**
     * Logs for Spring dependency management via autowiring processes (automatic).
     */
    public static class AUTOWIRING {

        public static String viaConstructor(Class c1, Class c2) {
            String via_constructor = "[AUTOWIRE] :  [%s] bean to [%s] bean via bean CONSTRUCTOR. ";
            return String.format(via_constructor, c1.getSimpleName(), c2.getSimpleName());
        }

        public static String viaSetter(Class c1, Class c2) {
            String via_setter = "[AUTOWIRE] :  [%s] bean to [%s] bean via bean SETTER method. ";
            return String.format(via_setter, c1.getSimpleName(), c2.getSimpleName());
        }
    }

}
