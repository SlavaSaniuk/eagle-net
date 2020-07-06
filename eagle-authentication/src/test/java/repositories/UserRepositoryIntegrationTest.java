package repositories;

import by.bsac.conf.DatasourcesConfiguration;
import by.bsac.conf.PersistenceConfiguration;
import by.bsac.models.User;
import by.bsac.repositories.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.transaction.annotation.Transactional;

@ActiveProfiles("TEST")
@SpringJUnitConfig({DatasourcesConfiguration.class, PersistenceConfiguration.class})
public class UserRepositoryIntegrationTest {

    @Autowired
    private UserRepository repository;

    @Test
    @Transactional
    void save_newUser_shouldReturnUserWithGeneratedId() {
        User user = new User();

        user = repository.save(user);

        Assertions.assertNotNull(user.getUserId());
        Assertions.assertNotEquals(0, user.getUserId());
    }

    @SuppressWarnings("OptionalGetWithoutIsPresent")
    @Test
    @Transactional
    void find_newUser_shouldReturnUserWithGeneratedId() {
        User user = new User();

        repository.save(user);

        User founded = repository.findById(1).get();

        Assertions.assertNotNull(founded.getUserId());
        Assertions.assertNotEquals(0, founded.getUserId());
    }

    @Test
    @Transactional
    void findByUserIdAlias_newUserWithIdAlias_shouldReturnUserWithGeneratedIdAndAlias() {
        User user = new User();
        user.setUserIdAlias("admin");

        repository.save(user);

        User founded = repository.findByUserIdAlias("admin");

        Assertions.assertNotNull(founded.getUserId());
        Assertions.assertNotEquals(0, founded.getUserId());
    }
}
