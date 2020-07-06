package by.bsac.services.accounts;

import by.bsac.core.logging.SpringCommonLogging;
import by.bsac.models.Account;
import by.bsac.services.CrudService;

public interface AccountsCrudService extends CrudService<Account, Integer> {

    @SuppressWarnings("AccessStaticViaInstance")
    SpringCommonLogging.BeanDefinition DEFINITION = SpringCommonLogging.BeanDefinition.of("AccountsCrudService").ofClass(AccountsCrudServiceImpl.class);

    Account getAccountByEmail(String account_email);

    Account getAccountByEmail(Account account);

}
