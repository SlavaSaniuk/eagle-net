package by.bsac.exceptions;

import lombok.Getter;
import lombok.Setter;

/**
 * This exception throws in case if feign {@link feign.codec.ErrorDecoder} con't to decode exception status.
 * This is a base class for remote microservices exception.
 */
@Getter @Setter
public class RemoteMicroserviceException extends RuntimeException {

    //Exception details
    private String remote_microservice;
    private Class local_microservice_interface;
    private String local_throws_method;

    public RemoteMicroserviceException() {
        super("Remote microservice method throws unhandled exception");
    }

    public RemoteMicroserviceException(String detailed_message) {
        super(detailed_message);
    }

    @Override
    public String toString() {
        return String.format("Exception throws in remote [%s] microservice when call method [%s] in local [%s]  feign client. Exception message: " +super.getMessage(),
                this.remote_microservice, this.local_throws_method, this.local_microservice_interface.getSimpleName());
    }
}
