package by.bsac.services.parsers;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Class is implements {@link PropertiesParser} interface and used to parse properties files/
 */
public class PropertiesFileParser implements PropertiesParser {

    private Properties props = new Properties(); //Parsed properties

    /**
     * Create new PropertiesFileParser object which load properties defined in {@link File} parameter.
     * @param a_property_file - {@link File} .properties file.
     * @throws IOException - Throws, if IO error occurs.
     */
    public PropertiesFileParser(File a_property_file) throws IOException {
        this.props.load(new FileInputStream(a_property_file));
    }

    /**
     * Create new PropertiesFileParser object which load properties from {@link InputStream} parameter.
     * Useful to parse resources {@code} getClass.getClassLoader.gerResourceAsStream("path-to-resource").
     * @param a_stream - {@link InputStream} which links on .properties file.
     * @throws IOException - Throws, if IO error occurs.
     */
    public PropertiesFileParser(InputStream a_stream) throws IOException {
        this.props.load(a_stream);
    }

    @Override
    public Properties getProperties() {
        if (props == null) throw new NullPointerException("Properties file not load.");
        return this.props;
    }

    @Override
    public String getProperty(String property_key) {
        if (props == null) throw new NullPointerException("Properties file not load.");
        if (property_key == null ) throw new NullPointerException("Property key string is null.");
        return this.props.getProperty(property_key);
    }
}
