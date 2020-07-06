package by.bsac.repositories;

import by.bsac.models.AccountStatus;
import by.bsac.models.Status;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository("AccountStatusRepository")
public interface AccountStatusRepository extends CrudRepository<AccountStatus, Integer> {

    @Query("UPDATE AccountStatus s SET s.status = :status WHERE s.status_id = :status_id")
    @Transactional
    @Modifying
    void updateAccountStatusById(@Param("status") Status status, @Param("status_id") Integer status_id);

    @Query("SELECT s FROM AccountStatus s WHERE s.status = :status")
    @Transactional
    List<AccountStatus> findAccountStatusByStatus(@Param("status") Status status);

}
