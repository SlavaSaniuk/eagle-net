package by.bsac.webmvc.controllers;

import by.bsac.core.beans.BasicDtoEntityConverter;
import by.bsac.core.beans.DtoEntityConverter;
import by.bsac.core.beans.EmbeddedDeConverter;
import by.bsac.core.logging.SpringCommonLogging;
import by.bsac.exceptions.AccountAlreadyRegisteredException;
import by.bsac.exceptions.AccountNotRegisteredException;
import by.bsac.exceptions.NoConfirmedAccountException;
import by.bsac.exceptions.NoCreatedDetailsException;
import by.bsac.feign.clients.AccountManagementService;
import by.bsac.feign.clients.AccountsCrudService;
import by.bsac.feign.clients.UserDetailsService;
import by.bsac.models.Account;
import by.bsac.models.User;
import by.bsac.models.UserDetails;
import by.bsac.models.UserName;
import by.bsac.webmvc.dto.UserWithAccountDto;
import by.bsac.webmvc.dto.UserWithDetailsDto;
import by.bsac.webmvc.forms.AccountForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import static by.bsac.conf.LoggerDefaultLogs.*;

@SuppressWarnings("AccessStaticViaInstance")
@Controller
@SessionAttributes({"common_user"})
public class SignController {

    //Logger
    private static final Logger LOGGER = LoggerFactory.getLogger(SignController.class);
    //Spring beans
    //Autowired via setter
    private AccountManagementService ams; //From FeignClientsConfiguration class
    private AccountsCrudService acs; //From FeignClientsConfiguration class
    private UserDetailsService uds; //From FeignClientsConfiguration class
    private EmbeddedDeConverter<UserWithDetailsDto> details_converter; //From DtoConvertersConfiguration class
    private DtoEntityConverter<UserWithAccountDto> account_converter; //From DtoConvertersConfiguration class

    //Model attributes
    @ModelAttribute("AccountForm")
    public AccountForm getAccountForm() {
        return new AccountForm();
    }

    @GetMapping("/sign")
    public ModelAndView getSignPage() {
        ModelAndView mav = new ModelAndView();

        mav.setViewName("sign");
        return mav;
    }

    @PostMapping("/signin")
    public ModelAndView loginAccount(@ModelAttribute("AccountForm") AccountForm account_form) throws NoCreatedDetailsException {
        //Create mav
        ModelAndView mav = new ModelAndView();

        //Try to login account
        Account account = account_form.toAccountEntity();
        User account_user;
        try {
            account_user = this.ams.loginAccount(account);
        }catch (AccountNotRegisteredException exc) {

            LOGGER.debug(exc.getMessage());
            mav.setViewName("redirect://sign?form=signup");
            return mav;

        } catch (NoConfirmedAccountException e) {
            LOGGER.warn(e.getMessage());
            throw new NoConfirmedAccountException(account);
        }

        //Get user details
        UserWithDetailsDto dto = new UserWithDetailsDto();
        dto.setUserId(account_user.getUserId());
        dto = this.uds.getDetails(dto);
        UserDetails details = this.details_converter.toEntity(dto, new UserDetails(), new UserName());
        account_user.setUserDetails(details);
        details.setDetailsUser(account_user);

        //Put user attribute to session
        mav.getModel().put("common_user", account_user);

        mav.setViewName("redirect:/user_" +account_user.getUserId());
        return mav;
    }

    @PostMapping("/signup")
    public ModelAndView registerAccount(@ModelAttribute("AccountForm") AccountForm account_form) {
        //Create mav
        ModelAndView mav = new ModelAndView();

        //Try to register account
        Account account = account_form.toAccountEntity();
        User account_user;
        try {
            account_user = this.ams.registerAccount(account);
        }catch (AccountAlreadyRegisteredException exc) {
            LOGGER.debug(exc.getMessage());
            mav.setViewName("sign");
            return mav;
        }

        //Put user attribute to session
        mav.getModel().put("common_user", account_user);
        return redirectToCreateDetailsView(mav);
    }

    @ExceptionHandler(NoConfirmedAccountException.class)
    public ModelAndView handleNoConfirmedAccount(NoConfirmedAccountException exc) {

        //Get account entity
        Account account = exc.getNoConfirmedAccount();

        //Try to find account by email
        UserWithAccountDto dto = this.account_converter.toDto(account, new UserWithAccountDto());
        dto = this.acs.getAccountByEmail(dto);

        //Create user entity
        User common_user = this.account_converter.toEntity(dto, new User());

        ModelAndView mav = new ModelAndView();
        mav.getModel().put("common_user", common_user);
        return this.redirectToCreateDetailsView(mav);
    }

    private ModelAndView redirectToCreateDetailsView(ModelAndView mav) {
        mav.setViewName("redirect:/about-user");
        return mav;
    }

    //Autowiring
    @Autowired
    public void setAccountManagementService(AccountManagementService a_ams) {
        LOGGER.debug(AUTOWIRING.viaSetter(a_ams.getClass(), this.getClass()));
        this.ams = a_ams;
    }

    @Autowired
    public void setAccountCrudService(AccountsCrudService a_acs) {
        LOGGER.debug(SpringCommonLogging.DependencyManagement.autowireViaSetter(SpringCommonLogging.BeanDefinition.of(AccountManagementService.class), this.getClass()));
        this.acs = a_acs;
    }

    @Autowired
    public void setUserDetailsService(UserDetailsService a_uds) {
        LOGGER.debug(AUTOWIRING.viaSetter(a_uds.getClass(), this.getClass()));
        this.uds = a_uds;
    }

    @Autowired
    public void setDetailsConverter(EmbeddedDeConverter<UserWithDetailsDto> a_converter) {
        LOGGER.debug(AUTOWIRING.viaSetter(a_converter.getClass(), this.getClass()));
        this.details_converter = a_converter;
    }

    @Autowired @Qualifier("UserWithAccountDtoConverter")
    public void setUserWithAccountDtoConverter(DtoEntityConverter<UserWithAccountDto> a_converter) {
        LOGGER.debug(SpringCommonLogging.DependencyManagement.autowireViaSetter(SpringCommonLogging.BeanDefinition.of("UserWithAccountDtoConverter").ofClass(BasicDtoEntityConverter.class).forGenericType(UserWithAccountDto.class), this.getClass()));
        this.account_converter = a_converter;
    }

}
