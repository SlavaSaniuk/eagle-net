package by.bsac.feign;

import by.bsac.exceptions.AccountAlreadyRegisteredException;
import by.bsac.exceptions.AccountNotRegisteredException;
import by.bsac.exceptions.NoConfirmedAccountException;
import by.bsac.exceptions.NoCreatedDetailsException;
import by.bsac.exceptions.commons.BadRequestException;
import by.bsac.webmvc.responses.ExceptionResponseBody;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Setter
public class FeignExceptionHandler implements ErrorDecoder {

    //Logger
    private static final Logger LOGGER = LoggerFactory.getLogger(FeignExceptionHandler.class);
    //Spring beans
    private ObjectMapper object_mapper;

    public Exception decode(String methodKey, Response response) {

        //Get remote exception object
        ExceptionResponseBody remote_exception = null;
        try {
            remote_exception = this.object_mapper.readValue(response.body().asReader(), ExceptionResponseBody.class);
        } catch (IOException e) {
            LOGGER.debug(e.getMessage());
        }

        //Decode remote exception by response status:
        switch (response.status()) {
            case 400:
                //Common exception
                return new BadRequestException();
            case 431: //return AccountAlreadyRegisteredException
                //Authentication microservice
                //Handle account already registered exception
                if (remote_exception != null) return new AccountAlreadyRegisteredException(remote_exception.getErrorMessage());
                else return new AccountAlreadyRegisteredException("Account with same email already register.");
            case 432:
                //Authentication microservice
                //Handle account not registered exception
                if (remote_exception != null) return new AccountNotRegisteredException(remote_exception.getErrorMessage()); //With detailed message
                else return new AccountNotRegisteredException(); //With default message
            case 433:
                //Authentication microservice
                //Handle NoConfirmedCccount exception
                if (remote_exception != null) return new NoConfirmedAccountException(remote_exception.getErrorMessage());
                else return new NoConfirmedAccountException("Target account no confirmed.");
            case 441: // NoCreatedDetailsException
                //Users microservice
                if (remote_exception != null) return new NoCreatedDetailsException(remote_exception.getErrorMessage()); //With detailed message
                else return new NoCreatedDetailsException();
        }

        return null;
    }

}
