package by.bsac.aspects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static by.bsac.core.logging.SpringCommonLogging.*;

@Configuration
public class BeansConfiguration {

    //Logger
    private static final Logger LOGGER = LoggerFactory.getLogger(BeansConfiguration.class);

    public BeansConfiguration() {
        LOGGER.debug(INITIALIZATION.startInitializeConfiguration(BeansConfiguration.class));
    }

    @Bean(name = "Foo")
    public Foo getFoo() {
        LOGGER.debug(CREATION.startCreateBean(BeanDefinition.of("Foo").ofClass(Foo.class)));
        return new Foo();
    }
}
