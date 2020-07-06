package by.bsac.aspects.validators;

import by.bsac.aspects.TestsAspectsConfiguration;
import by.bsac.core.validation.ParameterValidator;
import by.bsac.models.Account;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.ArrayList;
import java.util.List;

@ActiveProfiles("ASPECTS_DEBUG")
@SpringJUnitConfig(classes = {TestsAspectsConfiguration.class})
public class AccountCredentialsParameterValidatorIntegrationTest {

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired @Qualifier("AccountCredentialsValidator")
    private ParameterValidator validator;

    private final List<Object> list = new ArrayList<>();

    @AfterEach
    void tierDownAfterEach() {
        this.list.clear();
    }

    @Test
    void validate_invalidEmail__shouldReturnFalse() {
        Account account = new Account();
        account.setAccountEmail(null);
        account.setAccountPassword("asd");

        this.list.add(account);

        Assertions.assertFalse(this.validator.validate(this.list));
    }

    @Test
    void validate_invalidPassword__shouldReturnFalse() {
        Account account = new Account();
        account.setAccountPassword("");
        account.setAccountEmail("asd");

        this.list.add(account);

        Assertions.assertFalse(this.validator.validate(this.list));
    }

    @Test
    void validate_validEmail__shouldReturnFalse() {
        Account account = new Account();
        account.setAccountEmail("asdas");
        account.setAccountPassword("asd");

        this.list.add(account);

        Assertions.assertTrue(this.validator.validate(this.list));
    }

    @Test
    void validate_validPassword__shouldReturnFalse() {
        Account account = new Account();
        account.setAccountPassword("sadsa");
        account.setAccountEmail("asd");

        this.list.add(account);

        Assertions.assertTrue(this.validator.validate(this.list));
    }
}
