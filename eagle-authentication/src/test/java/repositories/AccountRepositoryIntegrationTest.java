package repositories;

import by.bsac.conf.DatasourcesConfiguration;
import by.bsac.conf.PersistenceConfiguration;
import by.bsac.models.Account;
import by.bsac.models.AccountStatus;
import by.bsac.models.Status;
import by.bsac.repositories.AccountRepository;
import by.bsac.repositories.AccountStatusRepository;
import by.bsac.services.ServicesConfiguration;
import by.bsac.services.accounts.AccountManagementService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@ActiveProfiles("TEST")
@SpringJUnitConfig({DatasourcesConfiguration.class, PersistenceConfiguration.class, ServicesConfiguration.class})
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Sql("account-imports.sql")
class AccountRepositoryIntegrationTest {

    @Autowired
    private AccountRepository account_repository;

    @Autowired
    private AccountStatusRepository status_repository;

    @Test
    @Transactional
    @Commit
    @Order(1)
    public void foundAllByAccountStatus_createdStatus_shouldReturnAccounts() {

        List<Account> founded = this.account_repository.foundAllByAccountStatus(Status.CREATED);

        Assertions.assertNotNull(founded);
        Assertions.assertEquals(3, founded.size());

    }

    @Test
    @Transactional
    @Commit
    @Order(2)
    public void updateAccountStatusById_oneStatusForUpdate_shouldUpdateStatus() {

        Account account = this.account_repository.foundByAccountEmail("test2@mail");
        assert account != null;

        this.status_repository.updateAccountStatusById(Status.CONFIRMED, account.getAccountId());

        List<AccountStatus> updated_statuses = this.status_repository.findAccountStatusByStatus(Status.CONFIRMED);

        Assertions.assertNotNull(updated_statuses);
        Assertions.assertEquals(1, updated_statuses.size());
        Assertions.assertEquals(account.getAccountId(), updated_statuses.get(0).getStatusId());

    }

    @Test
    @Order(3)
    public void findAccountStatusByStatus_threeCreatedStatuses_shouldReturnThis() {

        List<AccountStatus> statuses = this.status_repository.findAccountStatusByStatus(Status.CREATED);

        Assertions.assertNotNull(statuses);
        Assertions.assertEquals(3, statuses.size());
    }





}
