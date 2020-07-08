package by.bsac.configuration;

import org.apache.log4j.Level;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

import static by.bsac.core.logging.SpringCommonLogging.*;


/**
 *  {@link Log4jConfigurationBean} class is spring bean, which help user to set {@link org.apache.log4j.spi.RootLogger}
 * logger level via externalized configuration (command line arg., system/OS environment variables, application properties).
 *  User must specify {@link Log4jConfigurationBean#LOG4J_LOGGER_LEVEL_PROPERTY_NAME} property with
 *  following values: TRACE, DEBUG, INFO, WARN, CONSOLE in the externalized configuration.
 */
@Component("Log4jConfigurationBean")
@SuppressWarnings("unused")
public class Log4jConfigurationBean implements EnvironmentAware, InitializingBean {

    //Logger
    private static final Logger LOGGER = LoggerFactory.getLogger(Log4jConfigurationBean.class);
    //Spring beans
    public Environment environment;
    //Class variables
    public static final String LOG4J_LOGGER_LEVEL_PROPERTY_NAME = "log4j.configuration.rootLogger.level";
    public static final Map<String, Level> LOGGER_LEVEL_PROPERTY_VALUES_MAPPING = new HashMap<>();

    //Constructor
    /**
     * Construct new {@link Log4jConfigurationBean} spring bean.
     */
    public Log4jConfigurationBean() {
        LOGGER.debug(CREATION.startCreateBean(BeanDefinition.of(Log4jConfigurationBean.class)));
    }

    /**
     * Set root logger {@link Level} from argument specified via command line.
     * The FIRST method in order to set logger level from externalized configuration.
     * @return - 'true', if command line argument is defined;
     * @throws UnsupportedLoggerLevelValue - throws, if configuration property is exist but it's value is unsupported;
     */
    @Deprecated
    private boolean setLoggerLevelViaCommandLine() throws UnsupportedLoggerLevelValue {

        //Get property
        String value = this.environment.getProperty(LOG4J_LOGGER_LEVEL_PROPERTY_NAME);
        if (value == null) return false;

        Level mapped_value = checkSpecifiedValue(value);

        LOGGER.warn(String.format("Property [%s] is defined as command line argument with value [%s]; " +
                "Set LOG4J Root logger level.", LOG4J_LOGGER_LEVEL_PROPERTY_NAME, mapped_value.toString()));
        this.setLoggerLevel(mapped_value);
        return true;
    }

    /**
     * Set root logger {@link Level} with value specified via java {@link System} property.
     * This is SECOND method in order to set root logger level via externalized configuration.
     * @return - 'true', if system property is defined and it's value is supported.
     * @throws UnsupportedLoggerLevelValue - throws in case, when configuration property is exist but it's value is unsupported;
     */
    @Deprecated
    private boolean setLoggerLevelViaSystemProperty() throws UnsupportedLoggerLevelValue {

        //Get property
        String value = System.getProperty(LOG4J_LOGGER_LEVEL_PROPERTY_NAME);
        if (value == null) return false;

        Level mapped_value = checkSpecifiedValue(value);

        LOGGER.warn(String.format("Property [%s] is defined as java system property with value [%s]; " +
                "Set LOG4J Root logger level.", LOG4J_LOGGER_LEVEL_PROPERTY_NAME, mapped_value.toString()));
        this.setLoggerLevel(mapped_value);
        return true;

    }

    /**
     * Set root logger {@link Level} with value specified via java {@link System#getenv()} property.
     * This is SECOND method in order to set root logger level via externalized configuration.
     * @return - 'true', if system property is defined and it's value is supported.
     * @throws UnsupportedLoggerLevelValue - throws in case, when configuration property is exist but it's value is unsupported;
     */
    @Deprecated
    private boolean setLoggerLevelViaEnvironmentVariable() throws UnsupportedLoggerLevelValue {

        //Get property
        String value = this.environment.getProperty(LOG4J_LOGGER_LEVEL_PROPERTY_NAME);
        if (value == null) return false;

        Level mapped_value = checkSpecifiedValue(value);

        LOGGER.warn(String.format("Property [%s] is defined as operating system environment variable with value [%s];" +
                " Set LOG4J Root logger level.", LOG4J_LOGGER_LEVEL_PROPERTY_NAME, mapped_value.toString()));
        this.setLoggerLevel(mapped_value);
        return true;

    }

    /**
     * Set root logger {@link Level} with value specified via {@link Environment} spring bean.
     * This is FIRST and MAIN method in order to set root logger level via externalized configuration.
     * @return - 'true', if system property is defined and it's value is supported.
     * @throws UnsupportedLoggerLevelValue - throws in case, when configuration property is exist but it's value is unsupported;
     */
    private boolean setLoggerLevelViaEnvironment() throws UnsupportedLoggerLevelValue {

        //Get property
        String value = this.environment.getProperty(LOG4J_LOGGER_LEVEL_PROPERTY_NAME);
        if (value == null) return false;

        Level mapped_value = checkSpecifiedValue(value);

        LOGGER.warn(String.format("Property [%s] is defined as externalized property with value [%s];" +
                " Set LOG4J Root logger level.", LOG4J_LOGGER_LEVEL_PROPERTY_NAME, mapped_value.toString()));
        this.setLoggerLevel(mapped_value);
        return true;

    }

    /**
     * Check if specified value is supported by this configuration.
     * @param a_value - value for check. Should be one of the keys in {@link Log4jConfigurationBean#LOGGER_LEVEL_PROPERTY_VALUES_MAPPING};
     * @return - {@link Level} object for specified value;
     * @throws UnsupportedLoggerLevelValue - throws in cases when specified value is unsupported;
     */
    private Level checkSpecifiedValue(String a_value) throws UnsupportedLoggerLevelValue {
        a_value = a_value.toUpperCase();
        Level mapped_value = LOGGER_LEVEL_PROPERTY_VALUES_MAPPING.get(a_value);

        if (mapped_value == null) throw new UnsupportedLoggerLevelValue(a_value);
        else return mapped_value;
    }

    /**
     * Set Root logger {@link Level} to {@link org.apache.log4j.spi.RootLogger}.
     * @param logger_level - level to set;
     */
    public void setLoggerLevel(Level logger_level) {

        //Get Log4j root logger
        org.apache.log4j.Logger.getRootLogger().setLevel(logger_level);

    }

    /**
     * Get root logger {@link Level}.
     * @return - root logger level;
     */
    public Level getLoggerLevel() {
        //Get Log4j logger level
        return org.apache.log4j.Logger.getRootLogger().getLevel();
    }

    /**
     * Method initialize {@link Log4jConfigurationBean#LOGGER_LEVEL_PROPERTY_VALUES_MAPPING} with key=value
     * pairs in {@link Log4jConfigurationBean#afterPropertiesSet()} method.
     */
    private void initializeLoggerLevelPropertyValuesMapping() {

        LOGGER.debug("Initialize logger levels property values mapping;");

        LOGGER_LEVEL_PROPERTY_VALUES_MAPPING.put("TRACE", Level.TRACE);
        LOGGER_LEVEL_PROPERTY_VALUES_MAPPING.put("DEBUG", Level.DEBUG);
        LOGGER_LEVEL_PROPERTY_VALUES_MAPPING.put("INFO", Level.INFO);
        LOGGER_LEVEL_PROPERTY_VALUES_MAPPING.put("WARE", Level.WARN);
        LOGGER_LEVEL_PROPERTY_VALUES_MAPPING.put("ERROR", Level.ERROR);
    }

    //Dependency management
    @SuppressWarnings("NullableProblems")
    @Override
    public void setEnvironment(Environment environment) {
        LOGGER.debug(DependencyManagement.setViaSetter(BeanDefinition.of(Environment.class), Log4jConfigurationBean.class));
        this.environment = environment;
    }

    @Override
    public void afterPropertiesSet() throws Exception {

        final String NO_SPECIFIED_EXTERNAL_PROPERTY_MSG =
                String.format("No specified property [%s] via externalized configuration; " +
                        "Use value defined in [log4j.properties] file.", LOG4J_LOGGER_LEVEL_PROPERTY_NAME);

        //Check dependencies
        if (this.environment == null) throw new Exception(
                new BeanCreationException(DependencyManagement.Exceptions.nullProperty(Environment.class)));

        //Initialize LOGGER_LEVEL_PROPERTY_VALUES_MAPPING
        this.initializeLoggerLevelPropertyValuesMapping();

        //Try to set logger level
        try {
            if (this.setLoggerLevelViaEnvironment()) return;
        }catch (UnsupportedLoggerLevelValue exc) {
            LOGGER.warn(exc.getMessage());
        }

        //If property is not defined or unsupported
        LOGGER.warn(NO_SPECIFIED_EXTERNAL_PROPERTY_MSG);
    }

    //Inner classes
    /**
     * {@link UnsupportedLoggerLevelValue} runtime exception throws in cases when user or developer
     * set {@link Log4jConfigurationBean#LOG4J_LOGGER_LEVEL_PROPERTY_NAME} property with unsupported or invalid value.
     */
    public static class UnsupportedLoggerLevelValue extends RuntimeException {

        /**
         * Construct new runtime exception object with custom exception message.
         * @param a_value - unsupported or invalid value;
         */
        public UnsupportedLoggerLevelValue(String a_value) {
            super(String.format("Specified value [%s] for property [%s] is unsupported. " +
                    "Please, use one of the following values: TRACE, DEBUG, INFO, WARN, ERROR",
                    a_value, LOG4J_LOGGER_LEVEL_PROPERTY_NAME));
        }

    }
    
}
