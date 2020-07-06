package service.parser;

import by.bsac.services.parsers.TypesPropertiesParser;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.lang.reflect.InvocationTargetException;

class TypesPropertiesParserTestCase {

    private TypesPropertiesParser parser;

    @BeforeEach
    void setUpBeforeEach() throws IOException {
        this.parser = new TypesPropertiesParser(this.getClass().getClassLoader().getResourceAsStream("test-properties.properties"));
    }

    @AfterEach
    void tierDownAfterEach() {
        this.parser = null;
    }

    @Test
    void getTypeProperty_characterClass_shouldReturnCharacter() throws IOException, InvocationTargetException {
        Assertions.assertEquals('x', (char) this.parser.getTypeProperty("by.bsac.character", Character.class));
    }

    @Test
    void getTypeProperty_byteClass_shouldReturnByte() throws IOException, InvocationTargetException {
        Assertions.assertEquals(69, (byte) this.parser.getTypeProperty("by.bsac.byte", Byte.class));
    }

    @Test
    void getTypeProperty_integerClass_shouldReturnInteger() throws IOException, InvocationTargetException {
        Assertions.assertEquals(1488, (int) this.parser.getTypeProperty("by.bsac.integer", Integer.class));
    }


}
