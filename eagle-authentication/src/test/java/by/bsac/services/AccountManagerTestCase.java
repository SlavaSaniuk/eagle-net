package by.bsac.services;

import by.bsac.exceptions.NoConfirmedAccountException;
import by.bsac.models.Account;
import by.bsac.models.AccountStatus;
import by.bsac.repositories.AccountRepository;
import by.bsac.services.accounts.AccountManager;
import by.bsac.services.security.hashing.PasswordHash;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.DatatypeConverter;

public class AccountManagerTestCase {

    //Logger
    private static final Logger LOGGER = LoggerFactory.getLogger(AccountManagerTestCase.class);

    //Mocks
    @Mock
    private AccountRepository account_repository;

    @Mock
    private PasswordHash password_hasher;

    @InjectMocks
    private AccountManager manager;

    //Set up
    @BeforeEach
    void setUpBeforeEach() {
        MockitoAnnotations.initMocks(this);
    }

    @AfterEach
    void tierDownAfterEach() {
        this.manager = null;
    }

    @Test
    void login_noConfirmedAccount_shouldThrowNoConfirmedAccountException() {

        //Account entity
        Account account = new Account();
        account.setAccountEmail("ACCOUNT-EMAIL");
        account.setAccountPassword("12345678");

        //Founded account
        Account founded = new Account();
        founded.setAccountPasswordHash(DatatypeConverter.printHexBinary(DatatypeConverter.parseHexBinary("12345678")));
        founded.setAccountPasswordSalt("AAAA");

        AccountStatus status = new AccountStatus();
        founded.setAccountStatus(status);

        BDDMockito.given(this.account_repository.foundByAccountEmail(account.getAccountEmail())).willReturn(founded);

        //Passwords hashing
        BDDMockito.given(this.password_hasher.hashPassword(account.getAccountPassword(), DatatypeConverter.parseHexBinary(founded.getAccountPasswordSalt())))
                .willReturn(DatatypeConverter.parseHexBinary("12345678"));

        Assertions.assertThrows(NoConfirmedAccountException.class, () -> this.manager.login(account));

    }
}
