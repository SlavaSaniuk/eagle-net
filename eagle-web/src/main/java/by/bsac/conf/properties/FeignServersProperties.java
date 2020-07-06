package by.bsac.conf.properties;

import by.bsac.models.xml.FeignServer;
import by.bsac.models.xml.FeignServersModel;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static by.bsac.core.logging.SpringCommonLogging.*;

@Component
public class FeignServersProperties {

    //Logger
    private static final Logger LOGGER = LoggerFactory.getLogger(FeignServersProperties.class);
    //Class variables
    @Getter
    private final List<FeignServer> feign_servers = new ArrayList<>();

    //Constructor
    public FeignServersProperties(FeignServersModel a_model) {

        LOGGER.debug(CREATION.startCreateBean(BeanDefinition.of(this.getClass())));
        LOGGER.debug(DependencyManagement.setViaConstructor(BeanDefinition.of(FeignServersModel.class), this.getClass()));

        //Fill feign servers list
        a_model.getFeignServers()
                .forEach(m -> {FeignServer server = FeignServer.of(m); this.feign_servers.add(server);});
        //print feign servers
        feign_servers.forEach(fs -> LOGGER.debug(String.format("FeignServer:[%s];", fs.toString())));

        LOGGER.debug(CREATION.endCreateBean(BeanDefinition.of(this.getClass())));
    }

    @Nullable public FeignServer getServerById(int id) {
        return this.feign_servers.stream().filter(fs -> fs.getServerId() == id).findFirst().orElse(null);
    }


    @Nullable public FeignServer getServerByName(String name) {
        return this.feign_servers.stream().filter(fs -> fs.getServerName().equalsIgnoreCase(name)).findFirst().orElse(null);
    }

}
