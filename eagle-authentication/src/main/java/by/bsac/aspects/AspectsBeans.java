package by.bsac.aspects;

import by.bsac.aspects.debug.MethodCallAspect;
import by.bsac.aspects.debug.MethodExecutionTimeAspect;
import by.bsac.aspects.validation.ParameterValidationAspect;
import by.bsac.aspects.validators.AccountCredentialsParameterValidator;
import by.bsac.aspects.validators.AccountEmailParameterValidator;
import by.bsac.aspects.validators.AccountIdParameterValidator;
import by.bsac.aspects.validators.IdParameterValidator;
import by.bsac.core.debugging.LoggerLevel;
import by.bsac.core.validation.ParameterValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;

import static by.bsac.core.logging.SpringCommonLogging.*;

@SuppressWarnings("AccessStaticViaInstance")
@Configuration("AspectsBeans")
@Import(ParametersValidatorsConfiguration.class)
public class AspectsBeans {

    //Logger
    private static final Logger LOGGER = LoggerFactory.getLogger(AspectsBeans.class);
    //Spring beans
    private IdParameterValidator id_validator; //Autowired from ParametersValidatorsConfiguration class
    private AccountIdParameterValidator account_id_validator; //Autowired from ParametersValidatorsConfiguration class
    private ParameterValidator account_credentials_validator; //Autowired from ParametersValidatorsConfiguration class
    private ParameterValidator account_email_validator; //Autowired from ParametersValidatorsConfiguration class

    public AspectsBeans() {
        LOGGER.info(INITIALIZATION.startInitializeConfiguration(AspectsBeans.class));
    }

    @Bean(name = "MethodCallAspect")
    @Profile("ASPECTS_DEBUG")
    public MethodCallAspect getMethodCallAspect() {

        LOGGER.info(CREATION.startCreateBean(BeanDefinition.of("MethodCallAspect").ofClass(MethodCallAspect.class).forProfile("ASPECTS_DEBUG")));
        MethodCallAspect aspect = MethodCallAspect.aspectOf();

        aspect.setLoggerLevel(LoggerLevel.INFO);

        LOGGER.info(CREATION.endCreateBean(BeanDefinition.of("MethodCallAspect").ofClass(MethodCallAspect.class).forProfile("ASPECTS_DEBUG")));
        return aspect;
    }

    @Bean(name = "MethodExecutionTimeAspect")
    @Profile("ASPECTS_DEBUG")
    public MethodExecutionTimeAspect getMethodExecutionTimeAspect() {
        LOGGER.info(CREATION.startCreateBean(BeanDefinition.of("MethodExecutionTimeAspect").ofClass(MethodExecutionTimeAspect.class).forProfile("ASPECTS_DEBUG")));
        MethodExecutionTimeAspect aspect = MethodExecutionTimeAspect.aspectOf();

        aspect.setLoggerLevel(LoggerLevel.INFO);

        LOGGER.info(CREATION.endCreateBean(BeanDefinition.of("MethodExecutionTimeAspect").ofClass(MethodExecutionTimeAspect.class).forProfile("ASPECTS_DEBUG")));
        return aspect;
    }

    @Bean(name = "ParameterValidationAspect")
    public ParameterValidationAspect getParameterValidationAspect() {
        LOGGER.info(CREATION.startCreateBean(BeanDefinition.of("ParameterValidationAspect").ofClass(ParameterValidationAspect.class)));
        ParameterValidationAspect aspect = ParameterValidationAspect.aspectOf();

        aspect.addValidator(this.id_validator); //IdParameterValidator
        aspect.addValidator(this.account_id_validator); //AccountIdParameterValidator
        aspect.addValidator(this.account_email_validator); //AccountEmailValidator
        aspect.addValidator(this.account_credentials_validator); //AccountCredentialsValidator

        LOGGER.info(CREATION.endCreateBean(BeanDefinition.of("ParameterValidationAspect").ofClass(ParameterValidationAspect.class)));
        return aspect;
    }

    //Spring autowiring
    @Autowired @Qualifier("IdParameterValidator")
    public void setIdValidator(IdParameterValidator validator) {
        LOGGER.info(DependencyManagement.autowireViaSetter(BeanDefinition.of("IdParameterValidator").ofClass(IdParameterValidator.class), AspectsBeans.class));
        this.id_validator = validator;
    }

    @Autowired @Qualifier("AccountIdParameterValidator")
    public void setAccountIdValidator(AccountIdParameterValidator validator) {
        LOGGER.info(DependencyManagement.autowireViaSetter(BeanDefinition.of("AccountIdParameterValidator").ofClass(AccountIdParameterValidator.class), AspectsBeans.class));
        this.account_id_validator = validator;
    }

    @Autowired @Qualifier("AccountEmailValidator")
    public void setAccountEmailValidator(ParameterValidator account_email_validator) {
        LOGGER.info(DependencyManagement.autowireViaSetter(BeanDefinition.of("AccountEmailValidator").ofClass(AccountEmailParameterValidator.class), AspectsBeans.class));
        this.account_email_validator = account_email_validator;
    }

    @Autowired @Qualifier("AccountCredentialsValidator")
    public void setAccountCredentialsValidator(ParameterValidator account_credentials_validator) {
        LOGGER.info(DependencyManagement.autowireViaSetter(BeanDefinition.of("AccountCredentialsValidator").ofClass(AccountCredentialsParameterValidator.class), AspectsBeans.class));
        this.account_credentials_validator = account_credentials_validator;
    }
}
