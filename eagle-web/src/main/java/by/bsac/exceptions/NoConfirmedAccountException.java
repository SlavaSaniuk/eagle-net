package by.bsac.exceptions;

import by.bsac.models.Account;
import lombok.Getter;

/**
 * Exception throws in cases when user wants login with no confirmed account.
 */
@Getter
public class NoConfirmedAccountException extends RuntimeException {

    private Account no_confirmed_account;

    public NoConfirmedAccountException(String msg) {
        super(msg);
    }

    public NoConfirmedAccountException(Account account){
        super(String.format("No confirmed account[%s] try to login.", account.toString()));
        this.no_confirmed_account = account;
    }


}
