package by.bsac.exceptions.commons;

public class BadRequestException extends RuntimeException {

    public BadRequestException() {
        super("Http request failed with 400 status code.");
    }

}
