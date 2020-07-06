package by.bsac.webmvc.controllers;

import by.bsac.core.beans.EmbeddedDeConverter;
import by.bsac.exceptions.NoCreatedDetailsException;
import by.bsac.feign.clients.UserDetailsService;
import by.bsac.models.User;
import by.bsac.models.UserDetails;
import by.bsac.models.UserName;
import by.bsac.webmvc.dto.UserWithDetailsDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.servlet.ModelAndView;

import static by.bsac.conf.LoggerDefaultLogs.*;

@Controller
public class UserController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);
    //Spring beans
    private UserDetailsService uds; //Autowired via setter
    private EmbeddedDeConverter<UserWithDetailsDto> details_converter; //Autowired via setter

    public UserController() {
        LOGGER.debug(CREATION.beanCreationStart(this.getClass()));
        LOGGER.debug(CREATION.beanCreationFinish(this.getClass()));
    }


    @GetMapping(path = "/user_{given_id}")
    public ModelAndView getGivenUserPage(@SessionAttribute("common_user") User common_user, @PathVariable Integer given_id) throws NoCreatedDetailsException {
        final ModelAndView mav = new ModelAndView();

        //Check whether user access your own page
        if (common_user.getUserId().equals(given_id)) {
            mav.setViewName("forward:/common_user_page");
            return mav;
        }

        //Get given user details
        UserWithDetailsDto dto = new UserWithDetailsDto();
        dto.setUserId(given_id);

        dto = this.uds.getDetails(dto);
        User given_user = this.details_converter.toEntity(dto, new User());
        UserDetails given_details = this.details_converter.toEntity(dto, new UserDetails(), new UserName());

        given_user.setUserDetails(given_details);
        given_details.setDetailsUser(given_user);

        mav.getModel().put("given_user", given_user);

        mav.setViewName("given_user_page");
        return mav;
    }

    @GetMapping(path = "/common_user_page")
    public ModelAndView getCommonUserPage() {
        final ModelAndView mav = new ModelAndView();

        mav.setViewName("common_user_page");
        return mav;
    }

    //Autowiring
    @Autowired
    public void setUserDetailsService(UserDetailsService a_uds) {
        LOGGER.debug(AUTOWIRING.viaSetter(a_uds.getClass(), this.getClass()));
        this.uds = a_uds;
    }

    @Autowired
    public void setUserDetailsConverter(EmbeddedDeConverter<UserWithDetailsDto> converter) {
        LOGGER.debug(AUTOWIRING.viaSetter(converter.getClass(), this.getClass()));
        this.details_converter = converter;
    }
















}
