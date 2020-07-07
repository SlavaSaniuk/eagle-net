package by.bsac.configuration;

import org.apache.log4j.Level;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;

import java.util.HashMap;
import java.util.Map;

import static by.bsac.core.logging.SpringCommonLogging.*;

//@Component("Log4jConfigurationBean")
public class Log4jConfigurationBean implements EnvironmentAware, InitializingBean {

    //Logger
    private static final Logger LOGGER = LoggerFactory.getLogger(Log4jConfigurationBean.class);
    //Spring beans
    public Environment environment;
    //Class variables
    public static final String LOG4J_LOGGER_LEVEL_PROPERTY_NAME = "log4j.configuration.rootLogger.level";
    public static final Map<String, Level> LOGGER_LEVEL_PROPERTY_VALUES_MAPPING = new HashMap<>();

    //Constructor
    public Log4jConfigurationBean() {
        LOGGER.debug(CREATION.startCreateBean(BeanDefinition.of(Log4jConfigurationBean.class)));
    }

    /**
     * Set root logger {@link Level} from argument specified via command line.
     * The FIRST method in order to set logger level from externalized configuration.
     * @return - 'true', if command line argument is defined;
     */
    private boolean setLoggerLevelViaCommandLine() throws UnsupportedLoggerLevelValue {

        //Get property
        String value = this.environment.getProperty(LOG4J_LOGGER_LEVEL_PROPERTY_NAME);
        if (value == null) return false;

        Level mapped_value = checkSpecifiedValue(value);

        LOGGER.warn(String.format("Property [%s] is defined as command line argument with value [%s]; Set LOG4J Root logger level.", LOG4J_LOGGER_LEVEL_PROPERTY_NAME, mapped_value.toString()));
        this.setLoggerLevel(mapped_value);
        return true;
    }

    private boolean setLoggerLevelViaSystemProperty() throws UnsupportedLoggerLevelValue {

        //Get property
        String value = System.getProperty(LOG4J_LOGGER_LEVEL_PROPERTY_NAME);
        if (value == null) return false;

        Level mapped_value = checkSpecifiedValue(value);

        LOGGER.warn(String.format("Property [%s] is defined as command line argument with value [%s]; Set LOG4J Root logger level.", LOG4J_LOGGER_LEVEL_PROPERTY_NAME, mapped_value.toString()));
        this.setLoggerLevel(mapped_value);
        return true;

    }

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

    private static void initializeLoggerLevelPropertyValuesMapping() {

        LOGGER.debug("Initialize logger levels property values mapping;");

        LOGGER_LEVEL_PROPERTY_VALUES_MAPPING.put("TRACE", Level.TRACE);
        LOGGER_LEVEL_PROPERTY_VALUES_MAPPING.put("DEBUG", Level.DEBUG);
        LOGGER_LEVEL_PROPERTY_VALUES_MAPPING.put("INFO", Level.INFO);
        LOGGER_LEVEL_PROPERTY_VALUES_MAPPING.put("WARE", Level.WARN);
        LOGGER_LEVEL_PROPERTY_VALUES_MAPPING.put("ERROR", Level.ERROR);
    }

    //Dependency management
    public void setEnvironment(Environment environment) {
        LOGGER.debug(DependencyManagement.setViaSetter(BeanDefinition.of(Environment.class), Log4jConfigurationBean.class));
        this.environment = environment;
    }

    @Override
    public void afterPropertiesSet() throws Exception {

        //Check dependencies
        if (this.environment == null) throw new Exception(
                new BeanCreationException(DependencyManagement.Exceptions.nullProperty(Environment.class)));


        //Initialize LOGGER_LEVEL_PROPERTY_VALUES_MAPPING
        initializeLoggerLevelPropertyValuesMapping();

        //Try to set logger level
        if (this.setLoggerLevelViaCommandLine()) return; //Via command line argument

    }

    //Inner classes
    public static class UnsupportedLoggerLevelValue extends RuntimeException {

        public UnsupportedLoggerLevelValue(String a_value) {
            super(String.format("Specified value [%s] for property [%s] is unsupported. " +
                    "Please, use one of the following values: TRACE, DEBUG, INFO, WARN, ERROR",
                    a_value, LOG4J_LOGGER_LEVEL_PROPERTY_NAME));
        }

    }
    
}
