package by.bsac.webmvc.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/")
public class IndexController {

    @GetMapping
    public ModelAndView handleGetRequest() {

        ModelAndView mav = new ModelAndView();

        //Redirect to login page
        mav.setViewName("redirect:/sign");
        return mav;
    }

}
