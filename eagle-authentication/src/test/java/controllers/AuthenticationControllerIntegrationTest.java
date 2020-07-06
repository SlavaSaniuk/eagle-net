package controllers;

import by.bsac.conf.DatasourcesConfiguration;
import by.bsac.conf.PersistenceConfiguration;
import by.bsac.conf.RootContextConfiguration;
import by.bsac.models.Account;
import by.bsac.models.User;
import by.bsac.services.ServicesConfiguration;
import by.bsac.webmvc.WebmvcConfiguration;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ActiveProfiles("TEST")
@SpringJUnitWebConfig({WebmvcConfiguration.class, RootContextConfiguration.class, ServicesConfiguration.class, DatasourcesConfiguration.class, PersistenceConfiguration.class})
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class AuthenticationControllerIntegrationTest {

    private MockMvc mvc;
    private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticationControllerIntegrationTest.class);
    private final ObjectMapper mapper = new ObjectMapper();

    @BeforeEach
    void setUpBeforeEach(WebApplicationContext wac) {
        this.mvc = MockMvcBuilders.webAppContextSetup(wac).build();
        this.mapper.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);
        this.mapper.enable(DeserializationFeature.ACCEPT_EMPTY_ARRAY_AS_NULL_OBJECT);
    }

    @Test
    @Commit
    @Order(1)
    void register_newAccount_shouldReturnCreatedUser() throws Exception {

        Account account  = new Account();
        account.setAccountEmail("test@test.com");
        account.setAccountPassword("12345678");

        String account_json = mapper.writeValueAsString(account);
        LOGGER.debug("Account JSON: " +account_json);

        String response_body = this.mvc.perform(post("/register")
                .contentType("application/json").characterEncoding("UTF-8").content(account_json)).
                andDo(print()).
                andExpect(status().is(200))
        .andExpect(content().contentType("application/json"))
        .andReturn().getResponse().getContentAsString();

        User user = mapper.readValue(response_body, User.class);

        Assertions.assertNotNull(user);
        Assertions.assertEquals(1, user.getUserId());
    }

    @Test
    void register_registeredAccount_shouldThrowExceptionWithStatus431() throws Exception {

        Account account  = new Account();
        account.setAccountEmail("test@test.com");
        account.setAccountPassword("12345678");

        String response_body = this.mvc.perform(post("/register")
            .contentType("application/json").characterEncoding("UTF-8")
            .content(this.mapper.writeValueAsString(account)))
                .andDo(print())
                .andExpect(status().is(431))
                .andExpect(content().contentType("application/json"))
                .andReturn().getResponse().getContentAsString();

        LOGGER.debug("Error message: " +response_body);
    }

    @Test
    void login_registeredAccount_shouldReturnAccountUser() throws Exception {

        Account account = new Account();
        account.setAccountEmail("test@test.com");
        account.setAccountPassword("12345678");

        String response_body = this.mvc.perform(post("/login")
        .contentType("application/json").characterEncoding("UTF-8").content(this.mapper.writeValueAsString(account)))
                .andDo(print()).andExpect(status().is(200))
                .andExpect(content().contentType("application/json"))
                .andReturn().getResponse().getContentAsString();

        User user = this.mapper.readValue(response_body, User.class);

        Assertions.assertNotNull(user);
        Assertions.assertEquals(1, user.getUserId());

    }

    @Test
    void login_incorrectPasswordAccount_shouldReturnUserWithNegativeId() throws Exception {

        Account account = new Account();
        account.setAccountEmail("test@test.com");
        account.setAccountPassword("incorrect");

        String response_body = this.mvc.perform(post("/login")
                .contentType("application/json").characterEncoding("UTF-8").content(this.mapper.writeValueAsString(account)))
                .andDo(print())
                .andExpect(status().is(200))
                .andReturn().getResponse().getContentAsString();
        LOGGER.debug("Response body: " +response_body);

        User user = this.mapper.readValue(response_body, User.class);
        Assertions.assertNotNull(user);
        Assertions.assertEquals(-1, user.getUserId());
    }

    @Test
    void login_accountNotRegistered_shouldThrowExceptionWithStatus432() throws Exception {
        Account account = new Account();
        account.setAccountEmail("any-account-email");
        account.setAccountPassword("12345678");

        String response_body = this.mvc.perform(post("/login")
                .contentType("application/json").characterEncoding("UTF-8").content(this.mapper.writeValueAsString(account)))
                .andDo(print())
                .andExpect(status().is(432))
                .andReturn().getResponse().getContentAsString();

        LOGGER.debug("Response exception body JSON: " +response_body);
    }
}
