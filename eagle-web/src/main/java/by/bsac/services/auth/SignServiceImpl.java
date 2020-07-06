package by.bsac.services.auth;

import by.bsac.feign.clients.AccountManagementService;
import by.bsac.models.Account;
import by.bsac.models.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.InitializingBean;

import static by.bsac.core.logging.SpringCommonLogging.*;

public class SignServiceImpl implements SignService, InitializingBean {

    //Logger
    private static final Logger LOGGER = LoggerFactory.getLogger(SignServiceImpl.class);
    //Spring beans
    private AccountManagementService ams;

    //Constructor
    public SignServiceImpl() {
        LOGGER.debug(CREATION.startCreateBean(BeanDefinition.of(SignServiceImpl.class)));
    }

    @Override
    public User registerUser(Account an_account) {

        //Register user in "eagle-authentication" microservice
        User user = this.ams.registerAccount(an_account);



        return null;
    }

    @Override
    public void afterPropertiesSet() throws Exception {

        //Check dependencies
        if (this.ams == null)
            throw new Exception(new BeanCreationException(DependencyManagement.Exceptions.nullProperty(AccountManagementService.class)));

        LOGGER.debug(CREATION.endCreateBean(BeanDefinition.of(SignServiceImpl.class)));
    }
}
