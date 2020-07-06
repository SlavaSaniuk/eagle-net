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

@Component("AccountIdParameterValidator")
public class AccountIdParameterValidator implements ParameterValidator, InitializingBean {

    //Logger
    private static final Logger LOGGER = LoggerFactory.getLogger(AccountIdParameterValidator.class);
    //Spring beans
    private IdParameterValidator id_validator; //From ValidatorsConfiguration class

    //Constructor
    public AccountIdParameterValidator() {
        LOGGER.debug(CREATION.startCreateBean(BeanDefinition.of("AccountIdParameterValidator").ofClass(this.getClass())));
    }

    @Override
    public boolean validate(List<Object> list) {

        Account account = (Account) list.get(0);
        LOGGER.debug(String.format("Validate [account_id] property of account entity [%s];", account.toString()));

        return this.id_validator.validate(account.getAccountId());
    }

    public void setIdParameterValidator(IdParameterValidator a_validator) {
        LOGGER.debug(DependencyManagement.setViaSetter(BeanDefinition.of(IdParameterValidator.class), this.getClass()));
        this.id_validator = a_validator;
    }


    @Override
    public void afterPropertiesSet() throws Exception {
        if (this.id_validator == null)
            throw new BeanCreationException(String.format("Property of class [%s] is null.", IdParameterValidator.class.getCanonicalName()));

        LOGGER.debug(CREATION.endCreateBean(BeanDefinition.of("AccountIdParameterValidator").ofClass(this.getClass())));
    }
}
