package by.bsac.webmvc.controllers;

import by.bsac.exceptions.AccountNotRegisteredException;
import by.bsac.exceptions.EmailAlreadyRegisteredException;
import by.bsac.exceptions.NoConfirmedAccountException;
import by.bsac.models.Account;
import by.bsac.models.User;
import by.bsac.services.accounts.AccountManagementService;
import by.bsac.webmvc.responses.ExceptionResponseBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController("AuthenticationController")
public class AuthenticationController {

    //Logger
    private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticationController.class);
    //Spring beans
    private AccountManagementService ams; //Autowired via setter
    //Controller fields
    private static final int EMAIL_ALREADY_REGISTERED_EXCEPTION_STATUS_CODE = 431;
    private static final int ACCOUNT_NOT_REGISTERED_EXCEPTION_STATUS_CODE = 432;
    private static final int NO_CONFIRMED_ACCOUNT_EXCEPTION_STATUS_CODE = 433;

    @PostMapping(value = "/register", headers = {"content-type=application/json"}, produces = {"application/json"})
    public @ResponseBody User register(@RequestBody Account account) throws EmailAlreadyRegisteredException {
            return this.ams.register(account);
    }

    @PostMapping(value = "/login", headers = {"content-type=application/json"}, produces = {"application/json"})
    public User login(@RequestBody Account account) throws AccountNotRegisteredException, NoConfirmedAccountException {

            User user = this.ams.login(account);

            if (!(user == null)) return user;
            else {
                final User INVALID_USER = new User();
                INVALID_USER.setUserId(-1);
                return INVALID_USER;
            }
    }

    @ExceptionHandler(EmailAlreadyRegisteredException.class)
    public ResponseEntity<ExceptionResponseBody> handleEmailNotRegisterException(EmailAlreadyRegisteredException exc) {

        //Create exception body
        ExceptionResponseBody exception_body = new ExceptionResponseBody(exc);
        exception_body.setStatusCode(EMAIL_ALREADY_REGISTERED_EXCEPTION_STATUS_CODE);

        return ResponseEntity.status(EMAIL_ALREADY_REGISTERED_EXCEPTION_STATUS_CODE).contentType(MediaType.APPLICATION_JSON).body(exception_body);
    }

    @ExceptionHandler(AccountNotRegisteredException.class)
    public ResponseEntity<ExceptionResponseBody> handleAccountNotRegisteredException(AccountNotRegisteredException exc) {

        //Create exception body
        ExceptionResponseBody exception_body = new ExceptionResponseBody(exc);
        exception_body.setStatusCode(ACCOUNT_NOT_REGISTERED_EXCEPTION_STATUS_CODE);

        return ResponseEntity.status(ACCOUNT_NOT_REGISTERED_EXCEPTION_STATUS_CODE).contentType(MediaType.APPLICATION_JSON).body(exception_body);
    }

    /**
     * Controller method handle {@link NoConfirmedAccountException} exception in cases when
     * user want to login co confirmed account.
     * @param exc - {@link AccountNotRegisteredException} exception object.
     * @return - {@link ResponseEntity} object.
     */
    @ExceptionHandler(NoConfirmedAccountException.class)
    public ResponseEntity<ExceptionResponseBody> handleNoConfirmedAccountException(NoConfirmedAccountException exc) {

        //Create exception body
        ExceptionResponseBody exception_body = new ExceptionResponseBody(exc);
        exception_body.setStatusCode(NO_CONFIRMED_ACCOUNT_EXCEPTION_STATUS_CODE);

        return ResponseEntity.status(NO_CONFIRMED_ACCOUNT_EXCEPTION_STATUS_CODE).contentType(MediaType.APPLICATION_JSON).body(exception_body);
    }

    @Autowired
    public void setAccountManagementService(AccountManagementService a_ams) {
        LOGGER.debug("[AUTOWIRE] " +a_ams.getClass().getSimpleName() +" to " +getClass().getSimpleName() +" controller bean.");
        this.ams = a_ams;
    }
}
