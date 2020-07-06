package by.bsac.conf.properties;

import by.bsac.annotations.logging.Singleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@Singleton
public class ApplicationProperties {

    //Logger
    private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationProperties.class);
    //Class variables
    private static final ApplicationProperties APPLICATION_PROPERTIES = new ApplicationProperties();
    private final Properties CURRENT_APPLICATION_PROPERTIES = new Properties();
    private final Properties DEFAULT_APPLICATION_PROPERTIES = new Properties();

    //Private constructor
    private ApplicationProperties() {

        //Initialize default application properties
        this.initDefaultProperties();

        //load application properties from application.properties
        InputStream in = ApplicationProperties.class.getClassLoader().getResourceAsStream("application.properties");
        if (in != null) {

            try {
                CURRENT_APPLICATION_PROPERTIES.load(in);
                in.close();
            } catch (IOException e) {
                LOGGER.warn("IOException occurs when load application.properties. Use DEFAULT_APPLICATION_PROPERTIES.");
                CURRENT_APPLICATION_PROPERTIES.putAll(this.DEFAULT_APPLICATION_PROPERTIES);
            }

        }else {
            LOGGER.warn("Application.properties file not found. Use DEFAULT_APPLICATION_PROPERTIES.");
            CURRENT_APPLICATION_PROPERTIES.putAll(this.DEFAULT_APPLICATION_PROPERTIES);
        }

    }

    public static ApplicationProperties getInstance() {
        return APPLICATION_PROPERTIES;
    }

    private void initDefaultProperties() {

        this.DEFAULT_APPLICATION_PROPERTIES.put( // Feign active profile
                PROPERTIES_KEYS.FEIGN_ACTIVE_PROFILE_KEY, PROPERTIES_DEFAULT_VALUES.FEIGN_ACTIVE_PROFILE_DEFAULT);

    }

    public Properties getCurrentApplicationProperties() {
        return CURRENT_APPLICATION_PROPERTIES;
    }

    public static class PROPERTIES_KEYS {
        public static final String FEIGN_ACTIVE_PROFILE_KEY = "eagle.web.services.feign.active_profile";
    }

    public static class PROPERTIES_POSSIBLE_VALUES {
        public static final String[] FEIGN_ACTIVE_PROFILE_VALUES = {"FEIGN_PRODUCTION", "FEIGN_DEVELOPMENT", "FEIGN_TEST"};
    }

    public static class PROPERTIES_DEFAULT_VALUES {
        public static final String FEIGN_ACTIVE_PROFILE_DEFAULT = "FEIGN_DEVELOPMENT";
    }






}
