package by.bsac.services.accounts;

import by.bsac.annotations.validation.ParameterValidation;
import by.bsac.aspects.validators.AccountCredentialsParameterValidator;
import by.bsac.aspects.validators.AccountEmailParameterValidator;
import by.bsac.aspects.validators.AccountIdParameterValidator;
import by.bsac.aspects.validators.IdParameterValidator;
import by.bsac.models.Account;
import by.bsac.models.User;
import by.bsac.repositories.AccountRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import static by.bsac.core.logging.SpringCommonLogging.*;

@Component("AccountsCrudService")
public class AccountsCrudServiceImpl implements AccountsCrudService, InitializingBean {

    //Logger
    private static final Logger LOGGER = LoggerFactory.getLogger(AccountsCrudServiceImpl.class);
    //Spring beans
    private AccountRepository account_repository; //From persistence configuration class
    private AccountManagementService ams;


    public AccountsCrudServiceImpl() {
        LOGGER.debug(CREATION.startCreateBean(BeanDefinition.of("AccountsCrudService").ofClass(AccountsCrudServiceImpl.class)));
    }

    @SuppressWarnings("OptionalGetWithoutIsPresent")
    @Override
    @Transactional
    @ParameterValidation(value = AccountCredentialsParameterValidator.class, parametersClasses = Account.class, errorMessage = "Account credentials is in invalid value;")
    public Account create(Account entity) {
        User user = this.ams.register(entity);
        return this.account_repository.findById(user.getUserId()).get();
    }

    @SuppressWarnings("OptionalGetWithoutIsPresent")
    @Override
    @ParameterValidation(value = IdParameterValidator.class, parametersClasses = Integer.class, errorMessage = "Account ID is in invalid value;")
    public Account getById(Integer id) {
        return this.account_repository.findById(id).get();
    }


    @Override
    @Transactional
    @ParameterValidation(value = AccountIdParameterValidator.class, parametersClasses = Account.class, errorMessage = "Account ID is in invalid value;")
    public void delete(Account entity) {
        LOGGER.debug(String.format("Delete account entity with id [%d].", entity.getAccountId()));
        this.account_repository.deleteById(entity.getAccountId());
    }

    @Override
        public Account getAccountByEmail(String account_email) {
        LOGGER.debug(String.format("Get account entity by account email [%s].", account_email));
        return this.account_repository.foundByAccountEmail(account_email);
    }

    @Override
    @ParameterValidation(value = AccountEmailParameterValidator.class, parametersClasses = Account.class, errorMessage = "Account email is in invalid value;")
    public Account getAccountByEmail(Account account) {
        return this.getAccountByEmail(account.getAccountEmail());
    }

    @Override
    public void afterPropertiesSet() {
        //Check dependencies
        if (this.account_repository == null)
            throw new BeanCreationException(String.format("Dependency of [%s] is null.", AccountRepository.class.getCanonicalName()));

        if (this.ams == null)
            throw new BeanCreationException(String.format("Dependency of [%s] is null.", AccountManagementService.class.getCanonicalName()));

        LOGGER.debug(CREATION.endCreateBean(BeanDefinition.of("AccountsCrudService").ofClass(AccountsCrudServiceImpl.class)));
    }

    //Spring autowiring
    @Autowired
    public void setAccountRepository(AccountRepository a_account_repository) {
        LOGGER.debug(DependencyManagement.setViaSetter(BeanDefinition.of(AccountRepository.class), this.getClass()));
        this.account_repository = a_account_repository;
    }

    //Dependency management
    public void setAccountManagementService(AccountManagementService a_ams) {
        LOGGER.debug(DependencyManagement.setViaSetter(BeanDefinition.of(AccountManagementService.class), this.getClass()));
        this.ams = a_ams;
    }


}
