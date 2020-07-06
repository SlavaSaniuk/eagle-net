package converters;

import by.bsac.core.beans.DtoEntityConverter;
import by.bsac.models.Account;
import by.bsac.models.AccountStatus;
import by.bsac.models.Status;
import by.bsac.models.User;
import by.bsac.webmvc.DtoConvertersConfiguration;
import by.bsac.webmvc.dto.UserWithAccountDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@SpringJUnitConfig({DtoConvertersConfiguration.class})
public class UserWithAccountDtoConverter {

    //Logger
    private static final Logger LOGGER = LoggerFactory.getLogger(UserWithAccountDtoConverter.class);

    @Autowired
    private DtoEntityConverter<UserWithAccountDto> CONVERTER;

    @Test
    public void toDto_userAndAccountAndAccountStatusEntities_shouldReturnDto() {

        final Integer SHARED_ID = 24;

        User user = new User();
        user.setUserId(SHARED_ID);
        user.setUserIdAlias("USER-ID-ALIAS");

        Account account = new Account();
        account.setAccountId(SHARED_ID);
        account.setAccountEmail("ACCOUNT-EMAIL");

        AccountStatus status = new AccountStatus();
        status.setStatusId(SHARED_ID);
        status.setStatus(Status.CONFIRMED);

        UserWithAccountDto dto = new UserWithAccountDto();
        dto = this.CONVERTER.toDto(user, dto);
        dto = this.CONVERTER.toDto(account, dto);
        dto = this.CONVERTER.toDto(status, dto);

        Assertions.assertEquals(SHARED_ID, dto.getUserId());
        Assertions.assertEquals(SHARED_ID, dto.getAccountId());
        Assertions.assertEquals(SHARED_ID, dto.getStatusId());

        Assertions.assertEquals(account.getAccountEmail(), dto.getAccountEmail());
        Assertions.assertEquals(status.getStatus(), dto.getAccountStatus());

        LOGGER.debug("FINAL DTO: " +dto.toString());
    }

    @Test
    public void toEntity_newDto_shouldReturnThreeEntity() {

        final Integer SHARED_ID = 23;
        UserWithAccountDto dto = new UserWithAccountDto();
        dto.setAccountId(SHARED_ID);
        dto.setUserId(SHARED_ID);
        dto.setStatusId(SHARED_ID);
        dto.setAccountEmail("ACCOUNT-EMAIL");
        dto.setAccountStatus(Status.CONFIRMED);

        User user = this.CONVERTER.toEntity(dto, new User());
        Account account = this.CONVERTER.toEntity(dto, new Account());
        AccountStatus status = this.CONVERTER.toEntity(dto, new AccountStatus());

        Assertions.assertEquals(SHARED_ID, user.getUserId());

        Assertions.assertEquals(SHARED_ID, account.getAccountId());
        Assertions.assertEquals(dto.getAccountEmail(), account.getAccountEmail());

        Assertions.assertEquals(SHARED_ID, status.getStatusId());
        Assertions.assertEquals(dto.getAccountStatus(), status.getStatus());

        LOGGER.debug("Final DTO: " +dto.toString());

    }

}
