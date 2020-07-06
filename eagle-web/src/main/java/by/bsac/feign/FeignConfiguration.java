package by.bsac.feign;

import by.bsac.conf.properties.FeignServersProperties;
import by.bsac.core.logging.SpringCommonLogging;
import by.bsac.models.xml.FeignServersModel;
import by.bsac.services.xml.XmlConverter;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import feign.codec.Decoder;
import feign.codec.Encoder;
import feign.codec.ErrorDecoder;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import javax.xml.bind.JAXBException;

import static by.bsac.conf.LoggerDefaultLogs.*;

@SuppressWarnings("AccessStaticViaInstance")
@Configuration
@EnableFeignClients(defaultConfiguration = FeignConfiguration.class)
@Import(FeignClientsConfiguration.class) //Import feign clients beans
public class FeignConfiguration {

    //logger
    private static final Logger LOGGER = LoggerFactory.getLogger(FeignConfiguration.class);

    //Spring beans
    private XmlConverter<FeignServersModel> feign_servers_xml_converter; //Autowired from ServicesConfiguration class

    public FeignConfiguration() {
        LOGGER.info(INITIALIZATION.initConfig(this.getClass()));
    }

    @Bean("JacksonEncoder")
    public Encoder jacksonEncoder() {
        LOGGER.debug(CREATION.beanCreationStart(JacksonEncoder.class));
        final JacksonEncoder encoder = new JacksonEncoder(this.jacksonObjectMapper());

        LOGGER.debug(CREATION.beanCreationFinish(encoder.getClass()));
        return encoder;
    }

    @Bean("jacksonDecoder")
    public Decoder jacksonDecoder() {
        LOGGER.info(CREATION.beanCreationStart(Decoder.class));
        JacksonDecoder decoder = new JacksonDecoder(this.jacksonObjectMapper());

        LOGGER.info(CREATION.beanCreationFinish(decoder.getClass()));
        return decoder;
    }

    @Bean("ObjectMapper")
    public ObjectMapper jacksonObjectMapper() {
        LOGGER.info(CREATION.beanCreationFinish(ObjectMapper.class));
        ObjectMapper mapper = new ObjectMapper();

        mapper.configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, false);
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        mapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);

        LOGGER.info(CREATION.beanCreationFinish(mapper.getClass()));
        return mapper;
    }

    @Bean("FeignExceptionHandler")
    public ErrorDecoder feignExceptionHandler() {

        LOGGER.info(CREATION.beanCreationStart(ErrorDecoder.class));
        FeignExceptionHandler handler = new FeignExceptionHandler();

        handler.setObjectMapper(this.jacksonObjectMapper());

        LOGGER.info(CREATION.beanCreationFinish(handler.getClass()));
        return handler;
    }

    //Feign properties beans
    @Bean(name = "FeignServersProperties")
    public FeignServersProperties getFeignServerProperties() {

        LOGGER.info(SpringCommonLogging.CREATION.startCreateBean(SpringCommonLogging.BeanDefinition.of("FeignServerProperties").ofClass(FeignServersProperties.class)));
        LOGGER.info(SpringCommonLogging.DependencyManagement.setViaConstructor(SpringCommonLogging.BeanDefinition.of("FeignServersModel").ofClass(FeignServersModel.class), FeignServersProperties.class));
        FeignServersProperties properties = new FeignServersProperties(this.getFeignServersModel());

        LOGGER.info(SpringCommonLogging.CREATION.endCreateBean(SpringCommonLogging.BeanDefinition.of("FeignServerProperties").ofClass(FeignServersProperties.class)));
        return properties;

    }

    @Bean(name = "FeignServerModel")
    public FeignServersModel getFeignServersModel() {

        //Parse feign-servers xml file
        try {
            LOGGER.info(SpringCommonLogging.CREATION.startCreateBean(SpringCommonLogging.BeanDefinition.of("FeignServersModel").ofClass(FeignServersModel.class)));
            FeignServersModel model = this.feign_servers_xml_converter.convertToObject();

            LOGGER.info(SpringCommonLogging.CREATION.endCreateBean(SpringCommonLogging.BeanDefinition.of("FeignServersModel").ofClass(FeignServersModel.class)));
            return model;
        } catch (JAXBException e) {
            e.printStackTrace();
            LOGGER.info(SpringCommonLogging.CREATION.creationThrowExceptionWithMessage(
                    SpringCommonLogging.BeanDefinition.of("FeignServersModel").ofClass(FeignServersModel.class), e));
            throw new BeanCreationException(e.getMessage());
        }

    }

    //Spring autowiring
    @Autowired
    @Qualifier("FeignServersModelXmlConverter")
    public void setFeignServersXmlConverter(XmlConverter<FeignServersModel> a_converter) {
        LOGGER.info(SpringCommonLogging.DependencyManagement.autowireViaSetter(
                SpringCommonLogging.BeanDefinition.of("FeignServersModelXmlConverter").ofClass(XmlConverter.class).forGenericType(FeignServersModel.class), FeignConfiguration.class));
        this.feign_servers_xml_converter = a_converter;
    }

}
