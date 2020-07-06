package by.bsac;

import by.bsac.conf.RootContextConfiguration;
import by.bsac.conf.properties.PropertiesInitializer;
import by.bsac.webmvc.WebmvcConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

public class Main implements WebApplicationInitializer {

    //Logger
    private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);


    public void onStartup(javax.servlet.ServletContext servletContext) throws ServletException {

        //Create root spring application context
        LOGGER.info("Create spring \"Root application context\".");
        AnnotationConfigWebApplicationContext root_ctx = new AnnotationConfigWebApplicationContext();

        //Register configuration classes
        root_ctx.register(RootContextConfiguration.class);

        //Set active properties
        PropertiesInitializer.setActiveProfiles(root_ctx.getEnvironment());

        //Add listener on load root context
        servletContext.addListener(new ContextLoaderListener(root_ctx));
        LOGGER.info("Root application context was created.");

        //Create web spring application context
        LOGGER.info("Create spring \"Web application context\".");
        AnnotationConfigWebApplicationContext web_ctx = new AnnotationConfigWebApplicationContext();

        //Register configuration classes
        LOGGER.info("Register " + WebmvcConfiguration.class.getSimpleName() +" configuration class.");
        web_ctx.register(WebmvcConfiguration.class);

        //Create dispatcher servlet
        LOGGER.info("Create dispatcher servlet.");
        ServletRegistration.Dynamic dispatcher_servlet = servletContext.addServlet("dispatcher_servlet", new DispatcherServlet(web_ctx));
        LOGGER.info("Dispatcher servlet: mapping [ /* ].");
        dispatcher_servlet.addMapping("/");
        LOGGER.info("Dispatcher servlet: load on startup: 1");
        dispatcher_servlet.setLoadOnStartup(1);
        LOGGER.info("Dispatcher servlet was register.");
    }


}

