package by.bsac.conf.properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.ConfigurableEnvironment;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Optional;
import java.util.Properties;

public class PropertiesInitializer {

    //Logger
    private static final Logger LOGGER = LoggerFactory.getLogger(PropertiesInitializer.class);

    //Application properties
    private static final Properties DEFAULT_APPLICATION_PROPERTIES = loadDefaultApplicationProperties();
    private static final Properties CURRENT_APPLICATION_PROPERTIES = new Properties();

    public static void setActiveProfiles(ConfigurableEnvironment environment) {

        final String[] ACTIVE_PROFILES = new String[1];

        //Datasource active profile
        final String[] possible_profiles = {"DEVELOPMENT", "PRODUCTION", "TEST"};
        String datasource_active_profile = getCurrentApplicationProperties().getProperty(Keys.DATASOURCE_ACTIVE_PROFILE_KEY);
        Optional<String> datasource_active_profile_optional = Arrays.stream(possible_profiles).filter(s -> s.equalsIgnoreCase(datasource_active_profile)).findFirst();

        ACTIVE_PROFILES[0] = datasource_active_profile_optional.orElse(DefaultProperties.DEFAULT_DATASOURCE_ACTIVE_PROFILE);
        LOGGER.info(String.format("Datasource active profile [%s];", ACTIVE_PROFILES[0]));


        //Set active profiles
        environment.setActiveProfiles(ACTIVE_PROFILES);
    }

    public static Properties getCurrentApplicationProperties() {
        if (CURRENT_APPLICATION_PROPERTIES.isEmpty())
            loadApplicationProperties();
        return CURRENT_APPLICATION_PROPERTIES;
    }

    private static void loadApplicationProperties() {

        InputStream in = PropertiesInitializer.class.getClassLoader().getResourceAsStream("application.properties");
        if (in != null) {

            try {
                CURRENT_APPLICATION_PROPERTIES.load(in);
            } catch (IOException e) {
                LOGGER.warn(e.getMessage());
                LOGGER.warn("Application will use default application properties.");
                CURRENT_APPLICATION_PROPERTIES.putAll(DEFAULT_APPLICATION_PROPERTIES);
            }

        }else  {
            LOGGER.warn("File \"application.properties\" is absent. Application will use default application properties.");
            CURRENT_APPLICATION_PROPERTIES.putAll(DEFAULT_APPLICATION_PROPERTIES);
        }
    }

    private static Properties loadDefaultApplicationProperties() {

        Properties default_properties = new Properties();

        default_properties.put(Keys.DATASOURCE_ACTIVE_PROFILE_KEY, DefaultProperties.DEFAULT_DATASOURCE_ACTIVE_PROFILE);

        return default_properties;
    }

    public static class Keys {

        public static final String DATASOURCE_ACTIVE_PROFILE_KEY = "spring.profile.active.datasource";

    }

    public static class DefaultProperties {
        public static final String DEFAULT_DATASOURCE_ACTIVE_PROFILE = "DEVELOPMENT";
    }


}
