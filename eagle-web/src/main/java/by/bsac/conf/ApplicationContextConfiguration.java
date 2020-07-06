package by.bsac.conf;

import by.bsac.annotations.logging.Singleton;
import by.bsac.conf.properties.ApplicationProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.ConfigurableEnvironment;

import java.util.ArrayList;
import java.util.Arrays;

import static by.bsac.conf.properties.ApplicationProperties.*;

@Singleton
public class ApplicationContextConfiguration {

    //Logger
    private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationContextConfiguration.class);
    //Class variables
    private static final ApplicationContextConfiguration APPLICATION_CONTEXT_CONFIGURATION = new ApplicationContextConfiguration();

    //Private constructor
    private ApplicationContextConfiguration() {}

    public static ApplicationContextConfiguration getInstance() {
        return APPLICATION_CONTEXT_CONFIGURATION;
    }

    public void setActiveProfiles(ConfigurableEnvironment env) {
        LOGGER.debug("Start to set application ACTIVE profiles");
        final ArrayList<String> ACTIVE_PROFILES = new ArrayList<>();

        //Feign active profile
        String feign_active_profile_property = ApplicationProperties.getInstance().getCurrentApplicationProperties().getProperty(PROPERTIES_KEYS.FEIGN_ACTIVE_PROFILE_KEY);
        LOGGER.debug(String.format("Feign active profile specified property [%s]; Check it if available.", feign_active_profile_property));
        String feign_active_profile = Arrays.stream(PROPERTIES_POSSIBLE_VALUES.FEIGN_ACTIVE_PROFILE_VALUES).filter(p -> p.equalsIgnoreCase(feign_active_profile_property)).findFirst()
                .orElse(PROPERTIES_DEFAULT_VALUES.FEIGN_ACTIVE_PROFILE_DEFAULT);

        ACTIVE_PROFILES.add(feign_active_profile);

        // ...

        // Set active profiles
        final String[] RESULTING_ACTIVE_PROFILES = ACTIVE_PROFILES.toArray(new String[]{});
        LOGGER.debug(String.format("Active profiles array [%s];", Arrays.toString(RESULTING_ACTIVE_PROFILES)));

        env.setActiveProfiles(RESULTING_ACTIVE_PROFILES);
    }

}
