package by.bsac.webmvc.controllers;

import by.bsac.annotations.debug.MethodCall;
import by.bsac.annotations.debug.MethodExecutionTime;
import by.bsac.core.beans.DtoEntityConverter;
import by.bsac.models.Account;
import by.bsac.services.accounts.AccountManagementService;
import by.bsac.webmvc.dto.AccountWithStatusDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import static by.bsac.conf.LoggerDefaultLogs.*;

@RestController
public class AccountsStatusesManagementController {

    //Logger
    private static final Logger LOGGER = LoggerFactory.getLogger(AccountsStatusesManagementController.class);
    //Spring beans

    private DtoEntityConverter<AccountWithStatusDto> dto_converter;
    private AccountManagementService ams;

    public AccountsStatusesManagementController() {
        LOGGER.debug(CREATION.beanCreationStart(AccountsStatusesManagementController.class));
        LOGGER.debug(CREATION.beanCreationFinish(AccountsStatusesManagementController.class));
    }

    @MethodCall(withArgs = true, withStartTime = true) @MethodExecutionTime(inMicros = true, inNanos = false, inMillis = true)
    @PostMapping(value = "/confirm_account", headers = "content-type=application/json", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody public AccountWithStatusDto confirmAccount(@RequestBody AccountWithStatusDto dto) {

        Account account_to_confirm = this.dto_converter.toEntity(dto, new Account());

        Account confirmed = this.ams.confirmAccount(account_to_confirm);

        AccountWithStatusDto result = this.dto_converter.toDto(confirmed, new AccountWithStatusDto());
        return this.dto_converter.toDto(confirmed.getAccountStatus(), result);
    }


    @Autowired @Qualifier("AccountWithStatusDtoConverter")
    public void seAccountWithDtoConverter(DtoEntityConverter<AccountWithStatusDto> dto_converter) {
        LOGGER.debug(AUTOWIRING.viaSetter(dto_converter.getClass(), this.getClass()));
        this.dto_converter = dto_converter;
    }

    @Autowired
    public void setAccountManagementService(AccountManagementService ams) {
        LOGGER.debug(AUTOWIRING.viaSetter(ams.getClass(), this.getClass()));
        this.ams = ams;
    }
}
