package by.bsac.feign;

import by.bsac.conf.properties.FeignServersProperties;
import by.bsac.core.logging.SpringCommonLogging;
import by.bsac.feign.clients.AccountManagementService;
import by.bsac.feign.clients.AccountsCrudService;
import by.bsac.feign.clients.UserDetailsService;
import feign.Feign;
import feign.Request;
import feign.codec.Decoder;
import feign.codec.Encoder;
import feign.codec.ErrorDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import static by.bsac.conf.LoggerDefaultLogs.*;

@SuppressWarnings({"AccessStaticViaInstance", "DuplicatedCode", "ConstantConditions"})
@Configuration
public class FeignClientsConfiguration {

    //Logger
    private static final Logger LOGGER = LoggerFactory.getLogger(FeignClientsConfiguration.class);

    //Spring beans
    //Autowired via constructor
    private Encoder encoder;
    private Decoder decoder;
    private ErrorDecoder error_decoder;
    //Autowired via setter
    private FeignServersProperties feign_properties;

    //Constructor
    @Autowired
    public FeignClientsConfiguration(Encoder a_encoder, Decoder a_decoder, ErrorDecoder a_error_decoder) {

        LOGGER.info(INITIALIZATION.initConfig(this.getClass()));

        LOGGER.debug(AUTOWIRING.viaConstructor(a_encoder.getClass(), this.getClass()));
        this.encoder = a_encoder;

        LOGGER.debug(AUTOWIRING.viaConstructor(a_decoder.getClass(), this.getClass()));
        this.decoder = a_decoder;

        LOGGER.debug(AUTOWIRING.viaConstructor(a_error_decoder.getClass(), this.getClass()));
        this.error_decoder = a_error_decoder;

    }

    @Bean(name = "AccountManagementService")
    @Profile({"FEIGN_PRODUCTION", "FEIGN_DEVELOPMENT"})
    public AccountManagementService getProdDevAccountManagementService() {
        LOGGER.info(CREATION.beanCreationStart(AccountManagementService.class));
        AccountManagementService ams = Feign.builder()
                .encoder(this.encoder)
                .decoder(this.decoder)
                .errorDecoder(this.error_decoder)
                .target(AccountManagementService.class, "http://10.8.8.20:36547/eagle-auth/");
        LOGGER.info(CREATION.beanCreationFinish(ams.getClass()));
        return ams;
    }

    @SuppressWarnings("ConstantConditions")
    @Bean(name = "AccountManagementService")
    @Profile({"FEIGN_TEST"})
    public AccountManagementService getTestAccountManagementService() {
        LOGGER.info(SpringCommonLogging.CREATION.startCreateBean(SpringCommonLogging.BeanDefinition.of("AccountManagementService").ofClass(AccountManagementService.class).forProfile("FEIGN_TEST")));

        String URL = this.feign_properties.getServerByName("eagle-authentication-microservice-test").getFullServerPath();
        assert URL != null;

        AccountManagementService ams = Feign.builder()
                .encoder(this.encoder)
                .decoder(this.decoder)
                .errorDecoder(this.error_decoder)
                .target(AccountManagementService.class, URL);
        LOGGER.info(SpringCommonLogging.CREATION.endCreateBean(SpringCommonLogging.BeanDefinition.of("AccountManagementService").ofClass(AccountManagementService.class).forProfile("FEIGN_TEST")));
        return ams;
    }

    @Bean
    @Profile({"FEIGN_PRODUCTION", "FEIGN_DEVELOPMENT"})
    public AccountsCrudService getProdDevAccountsCrudService() {
        LOGGER.info(SpringCommonLogging.CREATION.startCreateBean(SpringCommonLogging.BeanDefinition.of(AccountManagementService.class).forProfile("FEIGN_PRODUCTION_DEVELOPMENT")));

        String URL = this.feign_properties.getServerByName("eagle-authentication-microservice").getFullServerPath();
        assert URL != null;

        AccountsCrudService ams = Feign.builder()
                .encoder(this.encoder)
                .decoder(this.decoder)
                .errorDecoder(this.error_decoder)
                .target(AccountsCrudService.class, URL);

        LOGGER.info(SpringCommonLogging.CREATION.endCreateBean(SpringCommonLogging.BeanDefinition.of(AccountManagementService.class).forProfile("FEIGN_PRODUCTION_DEVELOPMENT")));
        return ams;
    }

    @Bean
    @Profile({"FEIGN_TEST"})
    public AccountsCrudService getTestAccountsCrudService() {
        LOGGER.info(SpringCommonLogging.CREATION.startCreateBean(SpringCommonLogging.BeanDefinition.of(AccountManagementService.class).forProfile("FEIGN_TEST")));

        String URL = this.feign_properties.getServerByName("eagle-authentication-microservice-test").getFullServerPath();
        assert URL != null;

        AccountsCrudService ams = Feign.builder()
                .encoder(this.encoder)
                .decoder(this.decoder)
                .errorDecoder(this.error_decoder)
                .target(AccountsCrudService.class, URL);

        LOGGER.info(SpringCommonLogging.CREATION.endCreateBean(SpringCommonLogging.BeanDefinition.of(AccountManagementService.class).forProfile("FEIGN_TEST")));
        return ams;
    }

    @Bean(name = "UsersDetailsService")
    public UserDetailsService getUserDetailsService() {
        LOGGER.info(CREATION.beanCreationStart(UserDetailsService.class));
        UserDetailsService uds = Feign.builder()
                .encoder(this.encoder)
                .decoder(this.decoder)
                .errorDecoder(this.error_decoder)
                .options(new Request.Options(10000, 10000))
                .target(UserDetailsService.class, "http://10.8.8.25:36547/eagle-users-details");

        LOGGER.info(CREATION.beanCreationFinish(UserDetailsService.class));
        return uds;
    }

    //Spring autowiring
    @Autowired
    public void setFeignServersProperties(FeignServersProperties a_props) {
        LOGGER.info(SpringCommonLogging.DependencyManagement.autowireViaSetter(
                SpringCommonLogging.BeanDefinition.of("FeignServersProperties").ofClass(FeignServersProperties.class), FeignClientsConfiguration.class));
        this.feign_properties = a_props;
    }

}
