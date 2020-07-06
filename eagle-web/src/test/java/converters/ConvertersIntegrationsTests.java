package converters;

import by.bsac.core.beans.EmbeddedDeConverter;
import by.bsac.webmvc.DtoConvertersConfiguration;
import by.bsac.webmvc.dto.UserWithDetailsDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@SpringJUnitConfig(DtoConvertersConfiguration.class)
public class ConvertersIntegrationsTests {

    @Autowired
    EmbeddedDeConverter<UserWithDetailsDto> UserWithDetailsConverter;

    @Test
    void getUserWithDetailsDto_UserWithDetailsConverter_shouldReturnConverterBean() {
        Assertions.assertNotNull(this.UserWithDetailsConverter);
    }
}
