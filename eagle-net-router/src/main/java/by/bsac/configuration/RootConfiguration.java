package by.bsac.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static by.bsac.core.logging.SpringCommonLogging.*;

@Configuration("RootConfiguration")
public class RootConfiguration {

    //Logger
    private static final Logger LOGGER = LoggerFactory.getLogger(RootConfiguration.class);

    public RootConfiguration() {
        LOGGER.debug(INITIALIZATION.startInitializeConfiguration(RootConfiguration.class));
    }

    //Beans
    /**
     * Create {@link Log4jConfigurationBean} bean in application context.
     * @return - Configured {@link Log4jConfigurationBean} bean.
     */
    @Bean("Log4jConfigurationBean")
    public Log4jConfigurationBean getLog4jConfigurationBean() {
        LOGGER.info(CREATION.startCreateBean(BeanDefinition.of("Log4jConfigurationBean")));
        return new Log4jConfigurationBean();
    }
}
