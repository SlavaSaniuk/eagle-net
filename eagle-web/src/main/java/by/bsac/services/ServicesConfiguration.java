package by.bsac.services;

import by.bsac.models.xml.FeignServersModel;
import by.bsac.services.xml.XmlConverter;
import by.bsac.services.xml.XmlConverterImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.xml.bind.JAXBException;
import java.io.File;

import static by.bsac.core.logging.SpringCommonLogging.*;

@SuppressWarnings("AccessStaticViaInstance")
@Configuration
public class ServicesConfiguration {

    //Logger
    private static final Logger LOGGER = LoggerFactory.getLogger(ServicesConfiguration.class);

    //Constructor
    public ServicesConfiguration() {
        LOGGER.info(INITIALIZATION.startInitializeConfiguration(ServicesConfiguration.class));
    }

    @Bean("FeignServersModelXmlConverter")
    public XmlConverter<FeignServersModel> getFeignServersModelXmlConverter() {

        LOGGER.info(CREATION.startCreateBean(BeanDefinition.of("FeignServersModelXmlConverter").ofClass(XmlConverter.class).forGenericType(FeignServersModel.class)));
        File xml_file = new File(this.getClass().getClassLoader().getResource("feign-servers.xml").getPath());

        XmlConverter<FeignServersModel> parser;
        try {
            parser = new XmlConverterImpl<>(FeignServersModel.class, xml_file);
        } catch (JAXBException e) {
            LOGGER.info(CREATION.creationThrowExceptionWithMessage(
                    BeanDefinition.of("FeignServersModelXmlConverter").ofClass(XmlConverter.class).forGenericType(FeignServersModel.class), e));
            e.printStackTrace();
            throw new BeanCreationException(e.getMessage());
        }

        LOGGER.info(CREATION.endCreateBean(BeanDefinition.of("FeignServersModelXmlConverter").ofClass(XmlConverter.class).forGenericType(FeignServersModel.class)));
        return parser;
    }
}
