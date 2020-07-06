package by.bsac.services.xml;

import javax.xml.bind.JAXBException;

public interface XmlConverter<T> {

    public <T> T convertToObject() throws JAXBException;

    public <T> void convertToXml(T t) throws JAXBException;
}
