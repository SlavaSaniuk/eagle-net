package by.bsac.services.parsers;

import java.util.Properties;

/**
 * Common interface to parse files based on properties (key = value) format. Can be used in implemented classes to parse properties files, spring environment etc.
 */
public interface PropertiesParser {

    /**
     * Return parsed properties as java {@link Properties} object.
     * @return - {@link Properties} object with defined properties.
     */
    Properties getProperties();

    /**
     * Return property value by property key from implement class property object.
     * @param property_key - {@link String} key.
     * @return - {@link String} value.
     */
    String getProperty(String property_key);
}
