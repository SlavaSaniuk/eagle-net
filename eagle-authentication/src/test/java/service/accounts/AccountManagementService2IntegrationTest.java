package service.accounts;

import by.bsac.conf.DatasourcesConfiguration;
import by.bsac.conf.PersistenceConfiguration;
import by.bsac.models.Account;
import by.bsac.models.Status;
import by.bsac.repositories.AccountRepository;
import by.bsac.services.ServicesConfiguration;
import by.bsac.services.accounts.AccountManagementService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.List;

@ActiveProfiles("TEST")
@SpringJUnitConfig({DatasourcesConfiguration.class, PersistenceConfiguration.class, ServicesConfiguration.class})
@Sql("classpath:/repositories/account-imports.sql" )
public class AccountManagementService2IntegrationTest {

    @Autowired
    private AccountManagementService ams;

    @Autowired
    private AccountRepository account_repository;

    @Test
    public void confirmAccount_createdAccount_shouldUpdateAccountStatus() {

        Account account = this.account_repository.foundByAccountEmail("test1@mail");

        account =  this.ams.confirmAccount(account);

        Assertions.assertEquals(Status.CONFIRMED, account.getAccountStatus().getStatus());

        List<Account> confirmed = this.account_repository.foundAllByAccountStatus(Status.CONFIRMED);
        Assertions.assertEquals(1, confirmed.size());
        Assertions.assertEquals(account.getAccountId(), confirmed.get(0).getAccountId());
    }
}
