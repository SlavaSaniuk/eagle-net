package by.bsac.aspects.validators;

import by.bsac.core.validation.ParameterValidator;
import by.bsac.models.Account;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import java.util.List;

import static by.bsac.core.logging.SpringCommonLogging.*;

@Component("AccountCredentialsValidator")
public class AccountCredentialsParameterValidator implements ParameterValidator, InitializingBean {

    //Logger
    private static final Logger LOGGER = LoggerFactory.getLogger(AccountCredentialsParameterValidator.class);
    //Spring beans
    private ParameterValidator account_email_validator;

    //Constructor
    public AccountCredentialsParameterValidator() {
        LOGGER.debug(CREATION.startCreateBean(BeanDefinition.of("AccountCredentialsValidator").ofClass(AccountCredentialsParameterValidator.class)));
    }

    @Override
    public boolean validate(List<Object> list) {

        //Cast to account
        Account account = (Account) list.get(0);

        //Validate account_password
        //Check at null and emptiness
        if (account.getAccountPassword() == null || account.getAccountPassword().isEmpty()) {
            LOGGER.debug(String.format("Account [account_password] is in invalid value [%s];",account.getAccountEmail()));
            return false;
        }

        //Validate email
        return this.account_email_validator.validate(list);
    }

    @Override
    public void afterPropertiesSet() {

        if (this.account_email_validator == null)
            throw new BeanCreationException(String.format("Dependency of [%s] is null.", AccountEmailParameterValidator.class.getCanonicalName()));

        LOGGER.debug(CREATION.endCreateBean(BeanDefinition.of("AccountCredentialsValidator").ofClass(AccountCredentialsParameterValidator.class)));
    }

    //DependencyManagement
    public void setAccountEmailValidator(ParameterValidator validator) {
        LOGGER.debug(DependencyManagement.setViaSetter(BeanDefinition.of("AccountEmailValidator").ofClass(AccountEmailParameterValidator.class), this.getClass()));
        this.account_email_validator = validator;
    }
}
