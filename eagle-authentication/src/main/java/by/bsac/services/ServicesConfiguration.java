package by.bsac.services;

import by.bsac.core.logging.SpringCommonLogging;
import by.bsac.repositories.AccountRepository;
import by.bsac.repositories.AccountStatusRepository;
import by.bsac.repositories.UserRepository;
import by.bsac.services.accounts.AccountManagementService;
import by.bsac.services.accounts.AccountManager;
import by.bsac.services.accounts.AccountsCrudService;
import by.bsac.services.accounts.AccountsCrudServiceImpl;
import by.bsac.services.security.hashing.HashAlgorithm;
import by.bsac.services.security.hashing.PasswordHash;
import by.bsac.services.security.hashing.PasswordHasher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static by.bsac.conf.LoggerDefaultLogs.*;

@Configuration
public class ServicesConfiguration {

    //Logger
    private static final Logger LOGGER = LoggerFactory.getLogger(ServicesConfiguration.class);
    //Spring beans
    private AccountRepository account_repository;
    private UserRepository user_repository;
    private AccountStatusRepository status_repository;

    public ServicesConfiguration() {
        LOGGER.info("Start to initialize " +getClass().getSimpleName() +" configuration class.");
    }

    //Beans
    @Bean("PasswordHasher")
    public PasswordHash passwordHasher() {

        PasswordHasher hasher = new PasswordHasher();

        //Set "SHA-512" hash function
        hasher.setHashAlgorithm(HashAlgorithm.SHA_512);
        LOGGER.debug(hasher.getClass().getSimpleName() +": Use hash function [" +hasher.getHashAlgorithm()+"]");

        return hasher;
    }

    @Bean("AccountManagementService")
    public AccountManagementService accountManager() {

        AccountManager manager = new AccountManager();

        //Set properties
        manager.setPasswordHasher(this.passwordHasher());
        manager.setAccountRepository(this.account_repository);
        manager.setUserRepository(this.user_repository);
        manager.setStatusRepository(this.status_repository);

        return manager;
    }

    @Bean("AccountsCrudService")
    public AccountsCrudService getAccountsCrudService() {
        LOGGER.info(SpringCommonLogging.CREATION.startCreateBean(SpringCommonLogging.BeanDefinition.of("AccountsCrudService").ofClass(AccountsCrudServiceImpl.class)));
        AccountsCrudServiceImpl acs = new AccountsCrudServiceImpl();

        acs.setAccountRepository(this.account_repository);
        acs.setAccountManagementService(this.accountManager());

        LOGGER.info(SpringCommonLogging.CREATION.endCreateBean(SpringCommonLogging.BeanDefinition.of("AccountsCrudService").ofClass(AccountsCrudServiceImpl.class)));
        return acs;
    }

    //Spring autowiring
    @Autowired
    public void setAccountRepository(AccountRepository account_repository) {
        LOGGER.debug("[AUTOWIRE] " +account_repository.getClass().getSimpleName() +" to " +getClass().getSimpleName() +" configuration.");
        this.account_repository = account_repository;
    }

    @Autowired
    public void setUserRepository(UserRepository user_repository) {
        LOGGER.debug("[AUTOWIRE] " +user_repository.getClass().getSimpleName() +" to " +getClass().getSimpleName() +" configuration.");
        this.user_repository = user_repository;
    }

    @Autowired
    public void setAccountStatusRepository(AccountStatusRepository a_asr) {
        LOGGER.info(AUTOWIRING.viaSetter(AccountStatusRepository.class, ServicesConfiguration.class));
        this.status_repository = a_asr;
    }
}
