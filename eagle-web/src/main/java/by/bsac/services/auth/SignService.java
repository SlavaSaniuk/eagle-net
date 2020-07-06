package by.bsac.services.auth;

import by.bsac.models.Account;
import by.bsac.models.User;
import by.bsac.webmvc.dto.UserWithDetailsDto;

public interface SignService {

    /**
     * Method create new {@link User} entity for whole application. Also method must create any other {@link User} relative entities.
     * For example: New user register in application. Method must create {@link by.bsac.models.UserDetails} entity in "eagle-users-micro" microservice.
     * Use {@link by.bsac.feign.clients.UserDetailsService#createDetails(UserWithDetailsDto)} for this purpose.
     * @param an_account - {@link Account} to register.
     * @return - Registered {@link User} entity.
     */
    User registerUser(Account an_account);

}
