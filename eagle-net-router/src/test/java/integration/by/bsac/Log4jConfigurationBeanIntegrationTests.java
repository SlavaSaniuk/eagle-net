package integration.by.bsac;

import by.bsac.configuration.Log4jConfigurationBean;
import by.bsac.configuration.RootConfiguration;
import org.apache.log4j.Level;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SuppressWarnings("ALL")
@SpringBootTest(classes = RootConfiguration.class)
public class Log4jConfigurationBeanIntegrationTests {

    //Logger
    private static final Logger LOGGER = LoggerFactory.getLogger(Log4jConfigurationBeanIntegrationTests.class);
    //Spring beans
    @Autowired
    private Log4jConfigurationBean TEST_BEAN;

    @Test
    void setLoggerLevel_notSettedLoggerLevelViaCommandLine_shouldUseValueFromLog4jProperties() {
        Assertions.assertNotNull(TEST_BEAN);
        Assertions.assertEquals(Level.DEBUG, TEST_BEAN.getLoggerLevel());
    }

    @Test
    @Disabled
    void setLoggerLevelViaCommandLine_settedLoggerLevelViaCommandLine_shouldUseValueSpecifiedViaCommandLine() {
        //args = {"--log4j.configuration.rootLogger.level=INFO"}
        Assertions.assertNotNull(TEST_BEAN);
        Assertions.assertEquals(Level.INFO, TEST_BEAN.getLoggerLevel());
    }

}
