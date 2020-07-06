package by.bsac.webmvc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.templatemode.TemplateMode;

@Configuration
public class ThymeleafConfiguration {

    //Logger
    private static final Logger LOGGER = LoggerFactory.getLogger(ThymeleafConfiguration.class);
    //Spring beans
    private ApplicationContext application_context;

    @Bean
    public SpringResourceTemplateResolver templateResolver() {
        //Create template resolver
        SpringResourceTemplateResolver templateResolver = new SpringResourceTemplateResolver();
        templateResolver.setApplicationContext(this.application_context);
        //Tymeleaf view path
        templateResolver.setPrefix("/WEB-INF/views/");
        templateResolver.setSuffix(".html");
        //Template mode
        templateResolver.setTemplateMode(TemplateMode.HTML);
        //Disable cache
        templateResolver.setCacheable(false);

        //Return created template resolver
        return templateResolver;
    }

    @Bean
    public SpringTemplateEngine templateEngine() {
        SpringTemplateEngine template_engine = new SpringTemplateEngine();
        template_engine.setTemplateResolver(templateResolver());
        //Enable SpEL compiler (SpeedUp)
        template_engine.setEnableSpringELCompiler(true);
        return template_engine;
    }

    @Autowired
    public void setApplicationContext(ApplicationContext a_context) {
        LOGGER.debug("Autowire " +a_context.getClass().getName());
        this.application_context = a_context;
    }
}
