package by.bsac.conf;

import by.bsac.aspects.AspectsConfiguration;
import by.bsac.services.ServicesConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({DatasourcesConfiguration.class, PersistenceConfiguration.class,
        ServicesConfiguration.class, AspectsConfiguration.class})
public class RootContextConfiguration {



}
