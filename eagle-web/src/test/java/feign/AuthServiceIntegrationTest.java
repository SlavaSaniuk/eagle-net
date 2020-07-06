package feign;

import by.bsac.conf.RootContextConfiguration;
import by.bsac.exceptions.AccountAlreadyRegisteredException;
import by.bsac.exceptions.AccountNotRegisteredException;
import by.bsac.exceptions.NoConfirmedAccountException;
import by.bsac.feign.FeignClientsConfiguration;
import by.bsac.feign.FeignConfiguration;
import by.bsac.feign.clients.AccountManagementService;
import by.bsac.models.Account;
import by.bsac.models.User;
import org.junit.jupiter.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@SpringJUnitConfig({RootContextConfiguration.class, FeignConfiguration.class, FeignClientsConfiguration.class})
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class AuthServiceIntegrationTest {

    //Logger
    private static final Logger LOGGER = LoggerFactory.getLogger(AuthServiceIntegrationTest.class);

    @Autowired
    private AccountManagementService ams;

    @Test
    @Order(1)
    void register_newAccount_shouldReturnNewAccountUser() throws AccountAlreadyRegisteredException {
        Account account = new Account();
        account.setAccountEmail("test24@eagle-web.com");
        account.setAccountPassword("12345678");

        User user = this.ams.registerAccount(account);

        Assertions.assertNotNull(user);
        Assertions.assertNotNull(user.getUserId());

        LOGGER.debug(user.toString());
    }

    @Test
    @Order(2)
    void register_registerAccount_shouldThrowAccountAlreadyRegisteredException() {
        Account account = new Account();
        account.setAccountEmail("test1@eagle-web.com");
        account.setAccountPassword("any-password");

        Assertions.assertThrows(AccountAlreadyRegisteredException.class, ()-> this.ams.registerAccount(account));
    }

    @Test
    @Order(2)
    void login_registeredAccount_shouldReturnUserAccount() throws NoConfirmedAccountException {
        Account account = new Account();
        account.setAccountEmail("test1@eagle-web.com");
        account.setAccountPassword("12345678");

        User user = this.ams.loginAccount(account);

        Assertions.assertNotNull(user);
        Assertions.assertNotNull(user.getUserId());
        Assertions.assertNotEquals(0, user.getUserId());

        LOGGER.debug(user.toString());
        LOGGER.debug("User ID: " +user.getUserId().toString());
    }

    @Test
    @Order(3)
    void login_incorrectPassword_shouldReturnInvalidUserEntity() throws NoConfirmedAccountException {
        Account account = new Account();
        account.setAccountEmail("test1@eagle-web.com");
        account.setAccountPassword("invalid-password");

        User user = this.ams.loginAccount(account);

        Assertions.assertNotNull(user);
        Assertions.assertNotNull(user.getUserId());
        Assertions.assertEquals(-1, user.getUserId());

        LOGGER.debug(user.toString());
        LOGGER.debug("User ID: " +user.getUserId().toString());
    }

    @Test
    @Order(4)
    void login_accountNotRegister_shouldThrowAccountNotRegisteredException() {
        final Account account = new Account();
        account.setAccountEmail("not-register-email");
        account.setAccountPassword("any-password");

        Assertions.assertThrows(AccountNotRegisteredException.class, ()-> this.ams.loginAccount(account));
    }

}
