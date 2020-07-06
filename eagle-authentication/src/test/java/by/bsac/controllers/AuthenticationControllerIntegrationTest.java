package by.bsac.controllers;

import by.bsac.conf.DatasourcesConfiguration;
import by.bsac.conf.PersistenceConfiguration;
import by.bsac.models.Account;
import by.bsac.services.ServicesConfiguration;
import by.bsac.services.accounts.AccountManagementService;
import by.bsac.webmvc.WebmvcConfiguration;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ActiveProfiles("TEST")
@SpringJUnitWebConfig(classes = {DatasourcesConfiguration.class, PersistenceConfiguration.class,
        ServicesConfiguration.class, WebmvcConfiguration.class})
@Sql("classpath:/repositories/account-imports.sql")
public class AuthenticationControllerIntegrationTest {

    //Logger
    private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticationControllerIntegrationTest.class);

    @SuppressWarnings("unused")
    private MockMvc mock_mvc;

    //Spring beans
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired @Qualifier("ObjectMapper")
    private ObjectMapper object_mapper;
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private AccountManagementService ams; //From services configuration class

    @BeforeEach
    void setUpBeforeEach(WebApplicationContext wac) {
        this.mock_mvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @Test
    @Transactional
    @Commit
    void login_accountNoConfirmed_shouldReturnHttpErrorWithStatus433() throws Exception {

        Account account = new Account();
        account.setAccountEmail("any-email4@mail");
        account.setAccountPassword("12345678");

        this.ams.register(account);
        account.setAccountPassword("12345678");

        LOGGER.debug("Test account: " +account.toString());

        String req_json = this.object_mapper.writeValueAsString(account);
        LOGGER.debug("Request JSON: " +req_json);

        String resp_json = this.mock_mvc.perform(post("/login")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(req_json)
        ).andExpect(status().is(433))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse().getContentAsString();

        LOGGER.debug("Response JSON: " +resp_json);

    }

}
