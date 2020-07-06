package by.bsac.aspects;

import by.bsac.core.logging.SpringCommonLogging;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.*;
import org.springframework.instrument.classloading.LoadTimeWeaver;
import org.springframework.instrument.classloading.tomcat.TomcatLoadTimeWeaver;

@Configuration("AspectsConfiguration")
@Import(AspectsBeans.class)
@EnableLoadTimeWeaving(aspectjWeaving = EnableLoadTimeWeaving.AspectJWeaving.ENABLED)
@EnableAspectJAutoProxy
public class AspectsConfiguration implements LoadTimeWeavingConfigurer {

    private static final Logger LOGGER = LoggerFactory.getLogger(AspectsConfiguration.class);

    public AspectsConfiguration() {
        LOGGER.info(SpringCommonLogging.INITIALIZATION.startInitializeConfiguration(AspectsConfiguration.class));
    }

    @SuppressWarnings("NullableProblems")
    @Override
    public LoadTimeWeaver getLoadTimeWeaver() {
        return new TomcatLoadTimeWeaver();
    }
}
