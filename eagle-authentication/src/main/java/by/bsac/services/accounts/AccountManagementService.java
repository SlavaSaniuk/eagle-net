package by.bsac.services.accounts;

import by.bsac.exceptions.NoConfirmedAccountException;
import by.bsac.models.Account;
import by.bsac.models.AccountStatus;
import by.bsac.models.User;
import org.springframework.lang.Nullable;
import org.springframework.transaction.annotation.Transactional;

public interface AccountManagementService {

    @Transactional
    User register(Account account);

    /**
     * Method retrieve account entity from database by account email {@link Account#getAccountEmail()},
     * hash password and compare it's with retrieved entity password hash.
     * Return {@link User} entity if passwords are same, or null in other wise.
     * In cases when account entity has an account status {@link by.bsac.models.Status#CREATED}
     * method throw new {@link NoConfirmedAccountException}.
     * @param account - {@link Account} entity with email address.
     * @return - link User} entity if passwords are same, or null in other wise.
     * @throws NoConfirmedAccountException - If account entity has an account status {@link by.bsac.models.Status#CREATED}.
     */
    @Nullable User login(Account account) throws NoConfirmedAccountException;

    /**
     * Confirm user account. Method change {@link AccountStatus#getStatus()}
     * status to {@link by.bsac.models.Status#CONFIRMED} value. Use this
     * method when user create user_details information.
     * @param account - {@link Account} to confirm.
     * @return - {@link Account} with account CONFIRMED status.
     */
    @Transactional
    Account confirmAccount(Account account);

}
