package integration.by.bsac;

import by.bsac.configuration.Log4jConfigurationBean;
import by.bsac.configuration.RootConfiguration;
import by.bsac.core.logging.SpringCommonLogging;
import org.apache.log4j.Level;
import org.junit.jupiter.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@SuppressWarnings("ALL")
@SpringBootTest(classes = RootConfiguration.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class Log4jConfigurationBeanIntegrationTests implements EnvironmentAware {

    //Logger
    private static final Logger LOGGER = LoggerFactory.getLogger(Log4jConfigurationBeanIntegrationTests.class);
    //Spring beans
    private Environment ENV;
    @Autowired
    private Log4jConfigurationBean TEST_BEAN;

    @Test
    @Order(1)
    void setLoggerLevel_notSettedLoggerLevel_shouldUseValueFromLog4jProperties() {
        Assertions.assertNotNull(TEST_BEAN);
        Assertions.assertEquals(Level.DEBUG, TEST_BEAN.getLoggerLevel());
    }

    @Test
    @Order(2)
    @Disabled
    void setLoggerLevelViaCommandLine_settedLoggerLevelViaCommandLine_shouldUseValueSpecifiedViaCommandLine() {
        //args = {"--log4j.configuration.rootLogger.level=INFO"}
        Assertions.assertNotNull(TEST_BEAN);
        Assertions.assertEquals(Level.INFO, TEST_BEAN.getLoggerLevel());

        Assertions.assertEquals("INFO", this.ENV.getProperty("log4j.configuration.rootLogger.level"));
    }

    @Test
    @Order(3)
    @Disabled
    void setLoggerLevelViaJavaSystemVaraibles_settedLoggerLevel_shouldUseValueFromLog4jProperties() {
        //@ContextConfiguration(initializers = Log4jConfigurationBeanIntegrationTests.SystemPropertiesContextInitilizer.class)
        Assertions.assertNotNull(TEST_BEAN);
        Assertions.assertEquals(Level.INFO, TEST_BEAN.getLoggerLevel());

        Assertions.assertEquals("INFO", this.ENV.getProperty("log4j.configuration.rootLogger.level"));
    }

    @Override
    public void setEnvironment(Environment environment) {
        this.ENV = environment;
    }

    @Configuration
    public static class SystemPropertiesContextInitilizer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

        //Logger
        public static final Logger LOGGER = LoggerFactory.getLogger(SystemPropertiesContextInitilizer.class);

        public SystemPropertiesContextInitilizer() {
            LOGGER.info(SpringCommonLogging.INITIALIZATION.startInitializeConfiguration(SystemPropertiesContextInitilizer.class));
        }

        @Override
        public void initialize(ConfigurableApplicationContext configurableApplicationContext) {

            LOGGER.info("Set java system property [log4j.configuration.rootLogger.level] to [INFO] value");
            System.setProperty("log4j.configuration.rootLogger.level", "INFO");

        }
    }

}
