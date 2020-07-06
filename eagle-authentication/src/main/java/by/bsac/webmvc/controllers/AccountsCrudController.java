package by.bsac.webmvc.controllers;

import by.bsac.annotations.debug.MethodCall;
import by.bsac.core.beans.DtoEntityConverter;
import by.bsac.core.validation.exceptions.NoValidParameterException;
import by.bsac.models.Account;
import by.bsac.services.accounts.AccountsCrudService;
import by.bsac.services.accounts.AccountsCrudServiceImpl;
import by.bsac.webmvc.dto.UserWithAccountDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import static by.bsac.core.logging.SpringCommonLogging.*;

@RestController("AccountCrudController")
public class AccountsCrudController implements InitializingBean {

    //Logger
    private static final Logger LOGGER = LoggerFactory.getLogger(AccountsCrudController.class);

    //Spring beans
    private AccountsCrudService acs; //Autowired from ServicesConfiguration class
    private DtoEntityConverter<UserWithAccountDto> converter; //Autowired from DtoConvertersConfiguration class

    //Constructor
    public AccountsCrudController() {
        LOGGER.debug(CREATION.startCreateBean(BeanDefinition.of(AccountsCrudController.class)));
    }

    @PostMapping(value = "/get_by_email", headers = "content-type=application/json", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @MethodCall(withArgs = true, withStartTime = true, withReturnType = true)
    @ResponseBody
    public UserWithAccountDto getByEmail(@RequestBody UserWithAccountDto dto) {

        LOGGER.debug(String.format("Try to find account by account email[%s];", dto.getAccountEmail()));
        //Get account from dto
        Account account = this.converter.toEntity(dto, new Account());

        //Try to find account by email
        account = this.acs.getAccountByEmail(account);

        LOGGER.debug(String.format("Founded account is [%s];", account));
        UserWithAccountDto result = this.converter.toDto(account, new UserWithAccountDto());
        result = this.converter.toDto(account.getAccountStatus(), result);
        result = this.converter.toDto(account.getAccountUser(), result);

        LOGGER.debug(String.format("Resulting founded DTO object[%s];", dto));
        return result;
    }

    @ExceptionHandler(NoValidParameterException.class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Invalid target account [account_email] property.")
    @MethodCall(withArgs = true)
    public void onNoValidParameterException(NoValidParameterException exc) {
        LOGGER.warn(String.format("Exception [%s] occurs with message [%s]; ", exc.getClass(), exc.getMessage()));
    }


    @Override
    public void afterPropertiesSet() {

        //Check dependencies
        if (this.acs == null) {
                throw new BeanCreationException(String.format("Dependency of [%s] is null.", AccountsCrudService.class.getCanonicalName()));
        }

        if (this.converter == null) {
            throw new BeanCreationException(String.format("Dependency of [%s] is null.", DtoEntityConverter.class.getCanonicalName()));
        }

        LOGGER.debug(CREATION.endCreateBean(BeanDefinition.of(AccountsCrudController.class)));
    }

    //Spring autowiring
    @Autowired @Qualifier("AccountsCrudService")
    public void setAccountsCrudService(AccountsCrudService a_acs) {
        LOGGER.debug(DependencyManagement.autowireViaSetter(BeanDefinition.of("AccountsCrudService")
                .ofClass(AccountsCrudServiceImpl.class), this.getClass()));
        this.acs = a_acs;
    }

    @Autowired @Qualifier("UserWithAccountDtoConverter")
    public void setDtoEntityConverter(DtoEntityConverter<UserWithAccountDto> a_converter) {
        LOGGER.debug(DependencyManagement.autowireViaSetter(BeanDefinition.of("UserWithAccountDtoConverter")
                .ofClass(DtoEntityConverter.class).forGenericType(UserWithAccountDto.class), this.getClass()));
        this.converter = a_converter;
    }
}
