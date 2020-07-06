package converters;

import by.bsac.core.beans.EmbeddedDeConverter;
import by.bsac.core.beans.EmbeddedDtoEntityConverter;
import by.bsac.core.exceptions.NoSupportedEntitiesException;
import by.bsac.models.User;
import by.bsac.models.UserDetails;
import by.bsac.models.UserName;
import by.bsac.webmvc.dto.UserWithDetailsDto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserWithDetailsDtoConverterTestCase {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserWithDetailsDtoConverterTestCase.class);
    private EmbeddedDeConverter<UserWithDetailsDto> CONVERTER;

    @BeforeEach
    public void setUpBeforeEach() throws NoSupportedEntitiesException {
        this.CONVERTER = new EmbeddedDtoEntityConverter<>(UserWithDetailsDto.class);
    }

    @AfterEach
    public void tierDownAfterEach() {
        this.CONVERTER = null;
    }

    @Test
    public void toDto_newUserAndDetailsEntity_shouldReturnUserWithDetailsDtoObject() {

        final User user = new User();
        user.setUserId(23);

        final UserDetails details = new UserDetails();
        details.setUserName(new UserName("TEST", "TEST2"));

        UserWithDetailsDto dto = this.CONVERTER.toDto(user, new UserWithDetailsDto());
        dto = this.CONVERTER.toDto(details, dto);

        Assertions.assertEquals(user.getUserId(), dto.getUserId());

        LOGGER.debug(dto.toString());
    }

    @Test
    public void toEntity_newUserWithDetailsDto_shouldReturnUserAndDetailsEntities() {

        final UserWithDetailsDto dto = new UserWithDetailsDto();
        dto.setUserId(24);

        User user = this.CONVERTER.toEntity(dto, new User());
        UserDetails details = this.CONVERTER.toEntity(dto, new UserDetails(), new UserName());

        Assertions.assertEquals(dto.getUserId(), user.getUserId());

        LOGGER.debug(user.toString());
        LOGGER.debug(details.toString());

    }


}
