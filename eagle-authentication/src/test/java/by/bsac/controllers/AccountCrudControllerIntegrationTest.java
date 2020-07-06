package by.bsac.controllers;

import by.bsac.aspects.TestsAspectsConfiguration;
import by.bsac.conf.DatasourcesConfiguration;
import by.bsac.conf.PersistenceConfiguration;
import by.bsac.services.ServicesConfiguration;
import by.bsac.services.accounts.AccountsCrudService;
import by.bsac.webmvc.DtoConvertersConfiguration;
import by.bsac.webmvc.WebmvcConfiguration;
import by.bsac.webmvc.dto.UserWithAccountDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ActiveProfiles({"TEST", "ASPECTS_DEBUG"})
@SpringJUnitWebConfig(classes = {WebmvcConfiguration.class, DtoConvertersConfiguration.class,
        DatasourcesConfiguration.class, PersistenceConfiguration.class, ServicesConfiguration.class, TestsAspectsConfiguration.class})
public class AccountCrudControllerIntegrationTest {

    //Logger
    private static final Logger LOGGER = LoggerFactory.getLogger(AccountCrudControllerIntegrationTest.class);

    private MockMvc mvc;

    //Spring beans
    @Autowired @Qualifier("ObjectMapper")
    private ObjectMapper mapper;

    @Autowired @Qualifier("AccountsCrudService")
    private AccountsCrudService acs;

    @BeforeEach
    void setUpBeforeEach(WebApplicationContext wac) {
        this.mvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @Test
    void getById_accountWithEmail_shouldReturnUserWithAccountDto() throws Exception {

        final String ACCOUNT_EMAIL = "test1@mail";

        UserWithAccountDto dto = new UserWithAccountDto();
        dto.setAccountEmail(ACCOUNT_EMAIL);
        LOGGER.debug("Source UserWithAccountDto: " +dto);

        String src_json = this.mapper.writeValueAsString(dto);
        LOGGER.debug("Source JSON: " +src_json);

        String resp_json = this.mvc.perform(post("/get_by_email")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(src_json)
        ).andExpect(status().is(200)
        ).andExpect(content().contentType(MediaType.APPLICATION_JSON)
        ).andDo(print(System.out)
        ).andReturn().getResponse().getContentAsString();

        LOGGER.debug("Response JSON: " +resp_json);

        UserWithAccountDto resp_dto = this.mapper.readValue(resp_json, UserWithAccountDto.class);

        Assertions.assertNotNull(resp_dto);
        Assertions.assertNotNull(resp_dto.getAccountId());
        Assertions.assertEquals(ACCOUNT_EMAIL, resp_dto.getAccountEmail());

        LOGGER.debug("Response dto: " +resp_dto);

    }

    @Test
    void getById_dtoWithoutEmail_shouldReturnUserWithAccountDto() throws Exception {

        UserWithAccountDto dto = new UserWithAccountDto();
        LOGGER.debug("Source UserWithAccountDto: " +dto);

        String src_json = this.mapper.writeValueAsString(dto);
        LOGGER.debug("Source JSON: " +src_json);

        String exc_msg = this.mvc.perform(post("/get_by_email")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(src_json)
        ).andExpect(status().is(400)
        ).andReturn().getResponse().getContentAsString();

        LOGGER.debug("Exception message: " +exc_msg);

    }
}
