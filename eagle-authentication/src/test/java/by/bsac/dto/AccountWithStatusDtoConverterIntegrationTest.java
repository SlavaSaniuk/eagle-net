package by.bsac.dto;

import by.bsac.core.beans.DtoEntityConverter;
import by.bsac.models.Account;
import by.bsac.models.AccountStatus;
import by.bsac.models.Status;
import by.bsac.webmvc.DtoConvertersConfiguration;
import by.bsac.webmvc.dto.AccountWithStatusDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
@SpringJUnitConfig(classes = {DtoConvertersConfiguration.class})
public class AccountWithStatusDtoConverterIntegrationTest {

    //Logger
    private static final Logger LOGGER = LoggerFactory.getLogger(AccountWithStatusDtoConverterIntegrationTest.class);

    @Autowired
    private DtoEntityConverter<AccountWithStatusDto> converter;

    @Test
    void toDto_accountEntity_shouldReturnDtoWithDefinedAccountFields() {

        Account account = new Account();

        final String ACCOUNT_EMAIL = "TEST-EMAIL@TEST.COM";
        account.setAccountEmail(ACCOUNT_EMAIL);

        final Integer ACCOUNT_ID = 23;
        account.setAccountId(ACCOUNT_ID);

        AccountWithStatusDto dto = this.converter.toDto(account, new AccountWithStatusDto());

        Assertions.assertNotNull(dto);
        Assertions.assertEquals(ACCOUNT_ID, dto.getAccountId());
        Assertions.assertEquals(ACCOUNT_EMAIL, dto.getAccountEmail());

        LOGGER.debug("Dto object: " +dto.toString());
    }

    @Test
    void toDto_accountStatusEntity_shouldReturnDtoWithDefinedStatusField() {

        AccountStatus status = new AccountStatus();

        final Status account_status = Status.CONFIRMED;
        status.setStatus(account_status);

        AccountWithStatusDto dto = this.converter.toDto(status, new AccountWithStatusDto());

        Assertions.assertNotNull(dto);
        Assertions.assertEquals(account_status, dto.getAccountStatus());

        LOGGER.debug("Dto object: " +dto.toString());
    }

    @Test
    void toEntity_accountWithStatusDto_shouldReturnAccountEntity() {

        AccountWithStatusDto dto = new AccountWithStatusDto();

        final String ACCOUNT_EMAIL = "TEST-EMAIL@TEST.COM";
        dto.setAccountEmail(ACCOUNT_EMAIL);

        final Integer ACCOUNT_ID = 23;
        dto.setAccountId(ACCOUNT_ID);

        Account account = this.converter.toEntity(dto, new Account());

        Assertions.assertNotNull(account);
        Assertions.assertEquals(ACCOUNT_ID, account.getAccountId());
        Assertions.assertEquals(ACCOUNT_EMAIL, account.getAccountEmail());

        LOGGER.debug(String.format("Account entity {[account_id:%d], [account_email:%s]}", account.getAccountId(), account.getAccountEmail()));
    }

    @Test
    void toEntity_accountWithStatusDto_shouldReturnStatusEntity() {

        AccountWithStatusDto dto = new AccountWithStatusDto();

        final Status ACCOUNT_STATUS = Status.CONFIRMED;
        dto.setAccountStatus(ACCOUNT_STATUS);

        AccountStatus status = this.converter.toEntity(dto, new AccountStatus());

        Assertions.assertNotNull(status);
        Assertions.assertEquals(ACCOUNT_STATUS, status.getStatus());

        LOGGER.debug(String.format("AccountStatus entity {[account_status:%s]}", status.getStatus().toString()));
    }



}
