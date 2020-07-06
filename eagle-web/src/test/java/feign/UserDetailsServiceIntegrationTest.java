package feign;

import by.bsac.conf.RootContextConfiguration;
import by.bsac.core.beans.EmbeddedDeConverter;
import by.bsac.exceptions.NoConfirmedAccountException;
import by.bsac.exceptions.NoCreatedDetailsException;
import by.bsac.feign.FeignClientsConfiguration;
import by.bsac.feign.FeignConfiguration;
import by.bsac.feign.clients.AccountManagementService;
import by.bsac.feign.clients.UserDetailsService;
import by.bsac.models.Account;
import by.bsac.models.User;
import by.bsac.webmvc.DtoConvertersConfiguration;
import by.bsac.webmvc.dto.UserWithDetailsDto;
import org.junit.jupiter.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@SpringJUnitConfig({RootContextConfiguration.class, FeignConfiguration.class, FeignClientsConfiguration.class, DtoConvertersConfiguration.class})
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserDetailsServiceIntegrationTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserDetailsServiceIntegrationTest.class);

    //Spring beans
    @Autowired
    private UserDetailsService uds;

    @Autowired
    private AccountManagementService ams;

    @Autowired
    private EmbeddedDeConverter<UserWithDetailsDto> details_converter;

    @Test
    @Order(1)
    public void createDetains_registeredUser_shouldCreateAndReturnDto() {

        Account account = new Account();
        account.setAccountEmail("ACCOUNT-EMAIL");
        account.setAccountPassword("ACCOUNT-PASSWORD");

        User user = this.ams.registerAccount(account);

        UserWithDetailsDto dto = this.details_converter.toDto(user, new UserWithDetailsDto());
        dto.setFirstName("DETAILS-FIRST");
        dto.setLastName("DETAILS-LAST");

        dto = this.uds.createDetails(dto);

        Assertions.assertNotNull(dto);
        Assertions.assertEquals(user.getUserId(), dto.getUserId());
        Assertions.assertEquals(user.getUserId(), dto.getDetailId());

        LOGGER.debug("Details DTO: " +dto.toString());
    }

    @Test
    @Order(2)
    public void getDetails_registeredUser_shouldReturnUserWithDetailsDto() throws NoCreatedDetailsException, NoConfirmedAccountException {

        Account account = new Account();
        account.setAccountEmail("ACCOUNT-EMAIL");
        account.setAccountPassword("ACCOUNT-PASSWORD");

        User user = this.ams.loginAccount(account);

        UserWithDetailsDto dto = this.details_converter.toDto(user, new UserWithDetailsDto());

        dto = this.uds.getDetails(dto);

        Assertions.assertNotNull(dto);

        Assertions.assertEquals(user.getUserId(), dto.getDetailId());

        Assertions.assertNotNull(dto.getFirstName());
        Assertions.assertEquals("DETAILS-FIRST", dto.getFirstName());

        Assertions.assertNotNull(dto.getLastName());
        Assertions.assertEquals("DETAILS-LAST", dto.getLastName());

        LOGGER.debug("Details DTO: " +dto.toString());

    }

    @Test
    @Order(3)
    public void getDetails_registeredUserWithoutDetails_shouldThrowsNoCreatedDetailsException() {

        Account account = new Account();
        account.setAccountEmail("ACCOUNT-EMAIL2");
        account.setAccountPassword("ACCOUNT-PASSWORD");

        User user = this.ams.registerAccount(account);
        UserWithDetailsDto dto = this.details_converter.toDto(user, new UserWithDetailsDto());

        Assertions.assertThrows(NoCreatedDetailsException.class, ()-> this.uds.getDetails(dto));

    }


}
