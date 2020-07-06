package by.bsac.exceptions;

public class EmailAlreadyRegisteredException extends RuntimeException {

    public EmailAlreadyRegisteredException(String email)    {
        super(String.format("Account with email address [%s] already register", email));
    }
}
