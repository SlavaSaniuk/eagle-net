package by.bsac;

import by.bsac.configuration.RootConfiguration;
import by.bsac.configuration.noscan.NoScan;
import by.bsac.webmvc.WebmvcConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Import;

@SpringBootApplication(scanBasePackageClasses = NoScan.class)
@Import({RootConfiguration.class, WebmvcConfiguration.class})
public class Main extends SpringBootServletInitializer {

    //Logger
    private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);
    //Class variables
    public static final String MODULE_NAME = "eagle-net-router";


    public static void main(String[] args) {

        //Run Spring boot application
        LOGGER.info(String.format("Start [%s] module;", MODULE_NAME));
        SpringApplication.run(Main.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {

        //Disable Spring banner
        LOGGER.debug("Disable Spring banner;");
        builder.bannerMode(Banner.Mode.OFF);

        //Configure spring application
        return super.configure(builder);
    }
}
