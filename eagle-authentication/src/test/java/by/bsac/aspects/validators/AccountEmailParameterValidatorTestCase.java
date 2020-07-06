package by.bsac.aspects.validators;

import by.bsac.models.Account;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class AccountEmailParameterValidatorTestCase {

    private AccountEmailParameterValidator validator;
    private final List<Object> list = new ArrayList<>();

    @BeforeEach
    void setUpBeforeEach() {
        this.validator = new AccountEmailParameterValidator();
    }

    @AfterEach
    void tierDownAfterEach() {
        this.validator = null;
        this.list.clear();
    }

    @Test
    void validate_invalidEmail__shouldReturnFalse() {
        Account account = new Account();
        account.setAccountEmail(null);

        this.list.add(account);

        Assertions.assertFalse(this.validator.validate(this.list));
    }

    @Test
    void validate_invalidEmail2__shouldReturnFalse() {
        Account account = new Account();
        account.setAccountEmail("");

        this.list.add(account);

        Assertions.assertFalse(this.validator.validate(this.list));
    }

    @Test
    void validate_validEmail__shouldReturnFalse() {
        Account account = new Account();
        account.setAccountEmail("asdas");

        this.list.add(account);

        Assertions.assertTrue(this.validator.validate(this.list));
    }
}
