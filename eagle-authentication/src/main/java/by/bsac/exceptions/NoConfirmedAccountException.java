package by.bsac.exceptions;

/**
 * Exception throws in cases when user want to login account with status {@link by.bsac.models.Status#CREATED}.
 */
public class NoConfirmedAccountException extends Exception {

    /**
     * Construct new exception object with custom error message.
     * @param msg - custom error message.
     */
    public NoConfirmedAccountException(String msg) {
        super(msg);
    }

}
