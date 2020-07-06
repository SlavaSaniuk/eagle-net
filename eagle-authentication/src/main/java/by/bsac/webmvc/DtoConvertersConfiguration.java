package by.bsac.webmvc;

import by.bsac.core.beans.BasicDtoEntityConverter;
import by.bsac.core.beans.DtoEntityConverter;
import by.bsac.core.beans.EmbeddedDeConverter;
import by.bsac.core.exceptions.NoSupportedEntitiesException;
import by.bsac.webmvc.dto.AccountWithStatusDto;
import by.bsac.webmvc.dto.UserWithAccountDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static by.bsac.conf.LoggerDefaultLogs.*;

@Configuration
public class DtoConvertersConfiguration {

    //Logger
    private static final Logger LOGGER = LoggerFactory.getLogger(DtoConvertersConfiguration.class);

    public DtoConvertersConfiguration() {
        LOGGER.info(INITIALIZATION.initConfig(DtoConvertersConfiguration.class));
    }

    /**
     * This spring bean used to converter {@link UserWithAccountDto} DTO class
     * to entity classes and otherwise.
     * @return - {@link EmbeddedDeConverter}
     */
    @Bean(name = "UserWithAccountDtoConverter")
    public DtoEntityConverter<UserWithAccountDto> getUserWithAccountDtoConverter() {
        LOGGER.info(CREATION.beanCreationStart(EmbeddedDeConverter.class));

        try {
            final DtoEntityConverter<UserWithAccountDto> CONVERTER = new BasicDtoEntityConverter<>(UserWithAccountDto.class);
            LOGGER.info(CREATION.beanCreationFinish(DtoEntityConverter.class));
            return CONVERTER;
        } catch (NoSupportedEntitiesException e) {
            e.printStackTrace();
            throw new BeanCreationException(e.getMessage());
        }

    }

    @Bean(name = "AccountWithStatusDtoConverter")
    public DtoEntityConverter<AccountWithStatusDto> getAccountWithStatusDtoConverter() {

        LOGGER.info(CREATION.beanCreationStart(DtoEntityConverter.class));

        try {
            final DtoEntityConverter<AccountWithStatusDto> CONVERTER = new BasicDtoEntityConverter<>(AccountWithStatusDto.class);
            LOGGER.info(CREATION.beanCreationFinish(DtoEntityConverter.class));
            return CONVERTER;
        } catch (NoSupportedEntitiesException e) {
            e.printStackTrace();
            throw new BeanCreationException(e.getMessage());
        }

    }

}
