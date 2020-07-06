package by.bsac.webmvc;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

import static by.bsac.conf.LoggerDefaultLogs.*;

@Configuration
@ComponentScan("by.bsac.webmvc.controllers")
@EnableWebMvc
@Import(DtoConvertersConfiguration.class)
public class WebmvcConfiguration implements WebMvcConfigurer {

    //Logger
    private static final Logger LOGGER = LoggerFactory.getLogger(WebmvcConfiguration.class);

    public WebmvcConfiguration() {
        LOGGER.info(INITIALIZATION.initConfig(WebmvcConfiguration.class));
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {

        LOGGER.info(CREATION.beanCreationStart(MappingJackson2HttpMessageConverter.class));
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter(this.getObjectMapper());
        LOGGER.info(CREATION.beanCreationFinish(MappingJackson2HttpMessageConverter.class));

        converters.add(converter);
    }

    //Beans

    /**
     * Jackson {@link ObjectMapper} bean. Configured to use Jackson Snake_case property-naming strategy.
     * @return - {@link ObjectMapper} bean.
     */
    @Bean(name = "ObjectMapper")
    public ObjectMapper getObjectMapper() {
        LOGGER.info(CREATION.beanCreationStart(ObjectMapper.class));
        ObjectMapper mapper = new ObjectMapper();

        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, false);

        mapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);

        LOGGER.info(CREATION.beanCreationFinish(ObjectMapper.class));
        return mapper;
    }

}
