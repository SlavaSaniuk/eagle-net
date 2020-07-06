package by.bsac.webmvc.controllers;

import by.bsac.core.beans.EmbeddedDeConverter;
import by.bsac.feign.clients.UserDetailsService;
import by.bsac.models.User;
import by.bsac.models.UserDetails;
import by.bsac.models.UserName;
import by.bsac.webmvc.dto.UserWithDetailsDto;
import by.bsac.webmvc.forms.UserDetailsForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.servlet.ModelAndView;

import static by.bsac.conf.LoggerDefaultLogs.*;

@Controller
public class UserAboutController {

    //Logger
    private static final Logger LOGGER = LoggerFactory.getLogger(UserAboutController.class);
    //Spring beans
    private EmbeddedDeConverter<UserDetailsForm> details_form_converter;
    private EmbeddedDeConverter<UserWithDetailsDto> details_dto_converter;
    private UserDetailsService details_service; //Autowired via setter

    public UserAboutController() {
        LOGGER.debug(CREATION.beanCreationStart(UserAboutController.class));

        LOGGER.debug(CREATION.beanCreationFinish(UserAboutController.class));
    }


    @ModelAttribute("details_form")
    public UserDetailsForm getDetailsForm() {
        return new UserDetailsForm();
    }

    @GetMapping("/about-user")
    public ModelAndView getUserAboutPage() {
        //Model and view
        final ModelAndView mav = new ModelAndView();

        mav.setViewName("user_about");
        return mav;
    }

    @PostMapping("/about-user-create")
    public ModelAndView registerUserDetails(@ModelAttribute("details_form") UserDetailsForm details_dto, @SessionAttribute("common_user") User common_user) {

        //Model and view
        final ModelAndView mav = new ModelAndView();

        //Get details from form
        UserDetails details = this.details_form_converter.toEntity(details_dto, new UserDetails(), new UserName());

        //Create dto for "eagle-users-micro" microservice
        UserWithDetailsDto dto = this.details_dto_converter.toDto(details, new UserWithDetailsDto());
        dto = this.details_dto_converter.toDto(common_user, dto);

        //Try to create user details
        dto = this.details_service.createDetails(dto);

        //Convert to entities
        details = this.details_dto_converter.toEntity(dto, new UserDetails(), new UserName());
        common_user.setUserDetails(details);
        details.setDetailsUser(common_user);

        mav.setViewName("redirect:/user_" +common_user.getUserId());
        return mav;
    }

    @Autowired
    public void setDetailsFormConverter(EmbeddedDeConverter<UserDetailsForm> details_converter) {
        LOGGER.debug(AUTOWIRING.viaSetter(details_converter.getClass(), this.getClass()));
        this.details_form_converter = details_converter;
    }

    @Autowired
    public void setDetailsDtoConverter(EmbeddedDeConverter<UserWithDetailsDto> details_dto_converter) {
        this.details_dto_converter = details_dto_converter;
    }

    @Autowired
    public void setUserDetailsService(UserDetailsService details_service) {
        LOGGER.debug(AUTOWIRING.viaSetter(details_service.getClass(), this.getClass()));
        this.details_service = details_service;
    }
}
