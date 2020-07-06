package by.bsac.repositories;

import by.bsac.models.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {

    @Query("select u from User u where u.user_id_alias = ?1")
    User findByUserIdAlias(String user_id_alias);

}
