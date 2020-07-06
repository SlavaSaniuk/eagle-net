package by.bsac.services;

import by.bsac.annotations.debug.MethodCall;
import by.bsac.annotations.debug.MethodExecutionTime;
import by.bsac.aspects.AspectsBeans;
import by.bsac.aspects.TestsAspectsConfiguration;
import by.bsac.conf.DatasourcesConfiguration;
import by.bsac.conf.PersistenceConfiguration;
import by.bsac.core.validation.exceptions.NoValidParameterException;
import by.bsac.models.Account;
import by.bsac.services.accounts.AccountsCrudService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.transaction.annotation.Transactional;

@ActiveProfiles("TEST")
@SpringJUnitConfig(classes = {DatasourcesConfiguration.class, PersistenceConfiguration.class,
        ServicesConfiguration.class, TestsAspectsConfiguration.class, AspectsBeans.class})
@Sql("classpath:/repositories/account-imports.sql")
public class AccountCrudServiceIntegrationTest {

    //Logger
    private static final Logger LOGGER = LoggerFactory.getLogger(AccountCrudServiceIntegrationTest.class);
    //Spring beans
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private AccountsCrudService acs;

    @Test
    @MethodCall(withStartTime = true)
    @MethodExecutionTime(inMicros = true, inMillis = true)
    void getAccountByEmail_accountWithEmailAlreadyRegistered_shouldReturnAccountEntity() {

        final String ACCOUNT_EMAIL = "test1@mail";
        Account founded = this.acs.getAccountByEmail(ACCOUNT_EMAIL);

        Assertions.assertNotNull(founded);
        Assertions.assertEquals(1, founded.getAccountId());
        Assertions.assertEquals(ACCOUNT_EMAIL, founded.getAccountEmail());

        LOGGER.debug("Founded account: " +founded.toString());
    }

    @Test
    @MethodCall(withStartTime = true)
    @MethodExecutionTime(inMicros = true, inMillis = true)
    void getAccountByEmail_accountWithEmailAlreadyRegistered2_shouldReturnAccountEntity() {

        final String ACCOUNT_EMAIL = "test1@mail";
        Account account = new Account();
        account.setAccountEmail(ACCOUNT_EMAIL);

        Account founded = this.acs.getAccountByEmail(account);

        Assertions.assertNotNull(founded);
        Assertions.assertEquals(1, founded.getAccountId());
        Assertions.assertEquals(ACCOUNT_EMAIL, founded.getAccountEmail());

        LOGGER.debug("Founded account: " +founded.toString());
    }


    @Test
    @MethodCall(withStartTime = true, withArgs = true)
    @MethodExecutionTime(inMicros = true)
    void getById_accountIdIs1_shouldReturnAccountWithId1() {

        final Integer ACCOUNT_ID = 1;
        Account founded = this.acs.getById(ACCOUNT_ID);

        Assertions.assertEquals(ACCOUNT_ID, founded.getAccountId());
        LOGGER.debug("Founded account: " +founded.toString());
    }

    @Test
    @MethodCall(withStartTime = true, withArgs = true)
    @MethodExecutionTime(inMicros = true)
    void getById_accountIdIsNegativeValue_shouldThrowInvalidParameterException() {
        Assertions.assertThrows(NoValidParameterException.class, () -> this.acs.getById(-1));
    }

    @Test
    @MethodCall(withStartTime = true, withArgs = true)
    @MethodExecutionTime(inMicros = true)
    @Transactional
    @Commit
    void delete_accountIdIs1_shouldDeleteAccountWithId1() {
        final Integer ACCOUNT_ID = 1;

        Account account = new Account();
        account.setAccountId(ACCOUNT_ID);

        this.acs.delete(account);
    }

    @Test
    @MethodCall(withStartTime = true, withArgs = true)
    @MethodExecutionTime(inMicros = true)
    @Transactional
    @Commit
    void create_accountEmailAndPasswordAreSet_shouldReturnCreatedAccountEntity() {

        final String ACCOUNT_EMAIL = "ACCOUNT@EMAIL";

        Account account = new Account();
        account.setAccountEmail(ACCOUNT_EMAIL);
        account.setAccountPassword("12345678");

        Account created = this.acs.create(account);

        Assertions.assertNotNull(created);
        Assertions.assertNotNull(created.getAccountId());
        Assertions.assertEquals(ACCOUNT_EMAIL, created.getAccountEmail());

        LOGGER.debug("Created account entity: " +created.toString());

    }


}
