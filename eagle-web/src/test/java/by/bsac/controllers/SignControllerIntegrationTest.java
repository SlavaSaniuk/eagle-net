package by.bsac.controllers;

import by.bsac.conf.RootContextConfiguration;
import by.bsac.webmvc.WebmvcConfiguration;
import by.bsac.webmvc.forms.AccountForm;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.ModelAndView;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ActiveProfiles("FEIGN_TEST")
@SpringJUnitWebConfig(classes = {WebmvcConfiguration.class, RootContextConfiguration.class})
public class SignControllerIntegrationTest {

    //Logger
    private static final Logger LOGGER = LoggerFactory.getLogger(SignControllerIntegrationTest.class);

    private MockMvc mvc;

    @BeforeEach
    void setUpBeforeEach(WebApplicationContext wac) {
        this.mvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    //Spring beans
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired @Qualifier("ObjectMapper")
    private ObjectMapper mapper;

    @Test
    void loginAccount_validAccountCredentials_shouldRedirect() throws Exception {

        AccountForm login_form = new AccountForm();
        login_form.setAccountEmail("ACCOUNT@EMAIL");
        login_form.setAccountPassword("12345678");

        LOGGER.debug("Login form: " +login_form);

        String src_json = this.mapper.writeValueAsString(login_form);
        LOGGER.debug("SOURCE JSON: " +src_json);

        ModelAndView mav = this.mvc.perform(post("/signin")
                .flashAttr("AccountForm", login_form)
        ).andExpect(status().is(302)
        ).andReturn().getModelAndView();

        LOGGER.debug("MAV: " +mav);

    }


}
