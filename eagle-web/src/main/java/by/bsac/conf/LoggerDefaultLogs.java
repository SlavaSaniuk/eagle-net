package by.bsac.conf;

public class LoggerDefaultLogs {


    public static class INITIALIZATION {

        public static String initConfig(Class clazz) {
            final String INITIALIZE_CONFIGURATION = "Start to initialize [%s] configuration class.";
            return String.format(INITIALIZE_CONFIGURATION, clazz.getSimpleName());
        }
    }

    public static class CREATION {

        public static String beanCreationStart(Class bean_class) {
            final String CREATE_BEAN_START = "Start to create [%s] bean.";
            return String.format(CREATE_BEAN_START, bean_class.getCanonicalName());
        }

        public static String beanCreationFinish(Class bean_class) {
            final String CREATE_BEAN_FINISH = "Bean [%s] successfully was created.";
            return String.format(CREATE_BEAN_FINISH, bean_class.getCanonicalName());
        }

        public static String beanCreationFailed(Class<?> bean_class, Exception exc) {
            final String CREATE_BEAN_FAILED = "Create bean [%s] throws exception: {%s}";
            return String.format(CREATE_BEAN_FAILED, bean_class.getCanonicalName(), exc.getMessage());
        }

    }

    //Manual dependency management
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

    //Autowire spring beans
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
