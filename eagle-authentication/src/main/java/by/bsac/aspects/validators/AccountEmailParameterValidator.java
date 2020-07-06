package by.bsac.aspects.validators;


import by.bsac.core.validation.ParameterValidator;
import by.bsac.models.Account;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import java.util.List;

import static by.bsac.core.logging.SpringCommonLogging.*;

@Component("AccountEmailValidator")
public class AccountEmailParameterValidator implements ParameterValidator, InitializingBean {

    //Logger
    private static final Logger LOGGER = LoggerFactory.getLogger(AccountEmailParameterValidator.class);

    public AccountEmailParameterValidator() {
        LOGGER.debug(CREATION.startCreateBean(BeanDefinition.of("AccountEmailValidator").ofClass(AccountEmailParameterValidator.class)));
    }

    @Override
    public boolean validate(List<Object> list) {

        //Cast to account
        Account account = (Account) list.get(0);

        //Validate account_email
        //Check at null and emptiness
        if (account.getAccountEmail() == null || account.getAccountEmail().isEmpty()) {
            LOGGER.debug(String.format("Account [account_email] is in invalid value [%s];",account.getAccountEmail()));
            return false;
        }

        return true;
    }

    @Override
    public void afterPropertiesSet() {
        LOGGER.debug(CREATION.endCreateBean(BeanDefinition.of("AccountEmailValidator").ofClass(AccountEmailParameterValidator.class)));
    }
}
