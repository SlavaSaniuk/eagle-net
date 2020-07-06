package by.bsac.feign;

import by.bsac.exceptions.commons.BadRequestException;
import by.bsac.feign.clients.AccountsCrudService;
import by.bsac.services.ServicesConfiguration;
import by.bsac.webmvc.dto.UserWithAccountDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@ActiveProfiles("FEIGN_TEST")
@SpringJUnitConfig(classes = {ServicesConfiguration.class, FeignConfiguration.class})
public class AccountsCrudServiceIntegrationTest {

    //Logger
    private static final Logger LOGGER = LoggerFactory.getLogger(AccountsCrudServiceIntegrationTest.class);

    //Spring beans
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private AccountsCrudService acs;

    @Test
    void getByEmail_accountEmail_shouldReturnUserWithAccountDto() {

        final String ACCOUNT_EMAIL = "test2@mail";

        UserWithAccountDto dto = new UserWithAccountDto();
        dto.setAccountEmail(ACCOUNT_EMAIL);
        LOGGER.debug("Source DTO: " + dto);

        UserWithAccountDto founded = this.acs.getAccountByEmail(dto);

        Assertions.assertNotNull(founded);
        Assertions.assertNotNull(founded.getAccountId());
        Assertions.assertEquals(ACCOUNT_EMAIL, founded.getAccountEmail());

        LOGGER.debug("Founded DTO: " + founded);
    }

    @Test
    void getByEmail_invalidAccountEmail_shouldThrowBadRequestException() {

        UserWithAccountDto dto = new UserWithAccountDto();
        LOGGER.debug("Source DTO: " + dto);

        Exception exc = Assertions.assertThrows(BadRequestException.class, () -> this.acs.getAccountByEmail(dto));
        LOGGER.debug("Exception message: " + exc.getMessage());
    }

}
