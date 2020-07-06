package by.bsac.configuration;

import by.bsac.conf.ApplicationContextConfiguration;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mock.env.MockEnvironment;

import java.util.Arrays;

public class ApplicationContextConfigurationTestCase {

    //Logger
    private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationContextConfigurationTestCase.class);

    @Test
    @Disabled
    void setActiveProfiles_applicationFileWithProperties_shouldSetActiveProfiles() {

        MockEnvironment env = new MockEnvironment();

        final String[] EXPECTED_ACTIVE_PROFILES = {"FEIGN_TEST"};

        ApplicationContextConfiguration.getInstance().setActiveProfiles(env);

        String[] ACTUAL_ACTIVE_PROFILES = env.getActiveProfiles();
        LOGGER.debug("Actual active profiles: " + Arrays.toString(ACTUAL_ACTIVE_PROFILES));

        Assertions.assertEquals(EXPECTED_ACTIVE_PROFILES[0], ACTUAL_ACTIVE_PROFILES[0]);
    }

    @Test
    @Disabled
    void setActiveProfiles_applicationFileWithIncorrectProperties_shouldSetActiveProfiles() {

        MockEnvironment env = new MockEnvironment();

        final String[] EXPECTED_ACTIVE_PROFILES = {"FEIGN_DEVELOPMENT"};

        ApplicationContextConfiguration.getInstance().setActiveProfiles(env);

        String[] ACTUAL_ACTIVE_PROFILES = env.getActiveProfiles();
        LOGGER.debug("Actual active profiles: " + Arrays.toString(ACTUAL_ACTIVE_PROFILES));

        Assertions.assertEquals(EXPECTED_ACTIVE_PROFILES[0], ACTUAL_ACTIVE_PROFILES[0]);
    }

    @Test
    @Disabled
    void setActiveProfiles_applicationFileNotFound_shouldSetActiveProfiles() {

        MockEnvironment env = new MockEnvironment();

        final String[] EXPECTED_ACTIVE_PROFILES = {"FEIGN_DEVELOPMENT"};

        ApplicationContextConfiguration.getInstance().setActiveProfiles(env);

        String[] ACTUAL_ACTIVE_PROFILES = env.getActiveProfiles();
        LOGGER.debug("Actual active profiles: " + Arrays.toString(ACTUAL_ACTIVE_PROFILES));

        Assertions.assertEquals(EXPECTED_ACTIVE_PROFILES[0], ACTUAL_ACTIVE_PROFILES[0]);
    }

    @Test
    void setActiveProfiles_ignoreCAse_shouldSetActiveProfiles() {

        MockEnvironment env = new MockEnvironment();

        final String[] EXPECTED_ACTIVE_PROFILES = {"FEIGN_PRODUCTION"};

        ApplicationContextConfiguration.getInstance().setActiveProfiles(env);

        String[] ACTUAL_ACTIVE_PROFILES = env.getActiveProfiles();
        LOGGER.debug("Actual active profiles: " + Arrays.toString(ACTUAL_ACTIVE_PROFILES));

        Assertions.assertEquals(EXPECTED_ACTIVE_PROFILES[0], ACTUAL_ACTIVE_PROFILES[0]);
    }
}
