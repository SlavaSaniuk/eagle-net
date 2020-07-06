package service.accounts;

import by.bsac.annotations.debug.MethodCall;
import by.bsac.annotations.debug.MethodExecutionTime;
import by.bsac.aspects.TestsAspectsConfiguration;
import by.bsac.conf.DatasourcesConfiguration;
import by.bsac.conf.PersistenceConfiguration;
import by.bsac.exceptions.AccountNotRegisteredException;
import by.bsac.exceptions.EmailAlreadyRegisteredException;
import by.bsac.exceptions.NoConfirmedAccountException;
import by.bsac.models.Account;
import by.bsac.models.User;
import by.bsac.services.ServicesConfiguration;
import by.bsac.services.accounts.AccountManagementService;
import by.bsac.services.accounts.AccountsCrudService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@ActiveProfiles({"TEST", "ASPECTS_DEBUG"})
@SpringJUnitConfig({DatasourcesConfiguration.class, PersistenceConfiguration.class, ServicesConfiguration.class, TestsAspectsConfiguration.class})
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class AccountManagementServiceIntegrationTest {

    @Autowired
    private AccountManagementService ams;

    @Autowired
    private AccountsCrudService acs;

    @Test
    @Order(1)
    @Commit
    @MethodCall(withArgs = true, withStartTime = true)
    @MethodExecutionTime(inMicros = true, inMillis = true, inSeconds = true)
    @Disabled
    void registerAccount_newAccount_shouldReturnCreatedUser() {

        Account account = new Account();
        account.setAccountEmail("test@test.com");
        account.setAccountPassword("account-password");

        User user = this.ams.register(account);

        Assertions.assertNotNull(user);
        Assertions.assertNotNull(user.getUserId());

        Account persisted = user.getUserAccount();

        Assertions.assertNotNull(persisted);
        Assertions.assertNotNull(persisted.getAccountId());
        Assertions.assertEquals(persisted.getAccountId(), user.getUserId());
        Assertions.assertNull(persisted.getAccountPassword());
        Assertions.assertNotNull(persisted.getAccountPasswordHash());
        Assertions.assertNotNull(persisted.getAccountPasswordSalt());
    }

    @Test
    @Order(2)
    @MethodCall(withArgs = true, withStartTime = true)
    @MethodExecutionTime(inMicros = true, inMillis = true, inSeconds = true)
    void registerAccount_accountAlreadyRegister_shouldThrowEmailAlreadyRegisteredException() {

        Account account = new Account();
        account.setAccountEmail("test@test.com");
        account.setAccountPassword("any-password");

        Assertions.assertThrows(EmailAlreadyRegisteredException.class, ()-> this.ams.register(account));
    }

    @Test
    @MethodCall(withArgs = true, withStartTime = true)
    @MethodExecutionTime(inMicros = true, inMillis = true, inSeconds = true)
    void login_accountNotRegistered_shouldThrowAccountNotRegisteredException() {
        Account account = new Account();
        account.setAccountEmail("not-registered-email");
        account.setAccountPassword("any-password");

        Assertions.assertThrows(AccountNotRegisteredException.class, ()-> this.ams.login(account));
    }

    @Test
    @MethodCall(withArgs = true, withStartTime = true)
    @MethodExecutionTime(inMicros = true, inMillis = true, inSeconds = true)
    void login_accountRegistered_passwordIsIncorrect_shouldReturnNull() throws NoConfirmedAccountException {

        Account account = new Account();
        account.setAccountEmail("test@test.com");
        account.setAccountPassword("incorrect-password");

        Assertions.assertNull(this.ams.login(account));
    }

    @Test
    @MethodCall(withArgs = true, withStartTime = true)
    @MethodExecutionTime(inMicros = true, inMillis = true, inSeconds = true)
    void login_accountRegistered_passwordIsCorrect_shouldReturnAccountUser() throws NoConfirmedAccountException {

        Account account = new Account();
        account.setAccountEmail("test@test.com");

        account = this.acs.getAccountByEmail(account);
        this.ams.confirmAccount(account);

        account.setAccountPassword("account-password");
        Assertions.assertNotNull(this.ams.login(account));

    }
}
