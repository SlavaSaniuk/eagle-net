package by.bsac.feign;

import by.bsac.exceptions.NoConfirmedAccountException;
import by.bsac.feign.clients.AccountManagementService;
import by.bsac.models.Account;
import by.bsac.services.ServicesConfiguration;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@ActiveProfiles("FEIGN_TEST")
@SpringJUnitConfig(classes = {FeignConfiguration.class, ServicesConfiguration.class})
public class AccountManagementServiceIntegrationTest {

    //Logger
    private static final Logger LOGGER = LoggerFactory.getLogger(AccountManagementServiceIntegrationTest.class);

    //Spring beans
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired @Qualifier("AccountManagementService")
    private AccountManagementService ams;

    @Test
    void login_noConfirmedAccount_shouldThrowNoConfirmedAccountException() {

        Account account = new Account();
        account.setAccountEmail("test2@mail");
        account.setAccountPassword("12345678");

        LOGGER.debug("Test account entity: " +account);

        Assertions.assertThrows(NoConfirmedAccountException.class, () -> this.ams.loginAccount(account));

    }

}
