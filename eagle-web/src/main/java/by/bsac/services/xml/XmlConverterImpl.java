package by.bsac.services.xml;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;

import static by.bsac.core.logging.SpringCommonLogging.*;

@SuppressWarnings("AccessStaticViaInstance")
public class XmlConverterImpl<T> implements XmlConverter<T> {

    //Logger
    private static final Logger LOGGER = LoggerFactory.getLogger(XmlConverterImpl.class);
    //Fields
    private Class<T> pojo_class;
    private File xml_file;
    //JAXB fields
    private JAXBContext context;
    private Marshaller marshaller;
    private Unmarshaller unmarshaller;

    //Constructor
    public XmlConverterImpl(Class<T> a_pojo_class, File a_xml_file) throws JAXBException {
        LOGGER.debug(CREATION.startCreateBean(BeanDefinition.of(this.getClass()).forGenericType(a_pojo_class)));
        this.pojo_class = a_pojo_class;
        this.xml_file = a_xml_file;

        //Create context and marshaller/unmarshaller
        this.context = JAXBContext.newInstance(this.pojo_class);
        this.unmarshaller = context.createUnmarshaller();
        this.marshaller = context.createMarshaller();

        LOGGER.debug(CREATION.endCreateBean(BeanDefinition.of(this.getClass()).forGenericType(a_pojo_class)));
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T convertToObject() throws JAXBException {
        return (T) this.unmarshaller.unmarshal(this.xml_file);
    }

    @Override
    public <T> void convertToXml(T t) throws JAXBException {
        this.marshaller.marshal(t, this.xml_file);
    }
}
