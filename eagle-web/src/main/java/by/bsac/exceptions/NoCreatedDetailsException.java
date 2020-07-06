package by.bsac.exceptions;

import by.bsac.models.User;

/**
 * Exception throws in cases when {@link User} entity persisted in database,
 * but not persist {@link by.bsac.models.UserDetails} entity (Unfinished registration process).
 */
public class NoCreatedDetailsException extends Exception {

    /**
     * Construct new {@link NoCreatedDetailsException} exception object with detailed message.
      * @param msg - Details message.
     */
    public NoCreatedDetailsException(String msg) {
        super(msg);
    }

    public NoCreatedDetailsException() {
        super("Requested user registered, but doesn't have ti's own details.");
    }
}
