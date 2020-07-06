package by.bsac.aspects;

import by.bsac.core.logging.SpringCommonLogging;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.*;
import org.springframework.instrument.classloading.InstrumentationLoadTimeWeaver;
import org.springframework.instrument.classloading.LoadTimeWeaver;

@SuppressWarnings("NullableProblems")
@Configuration("TestsAspectsConfiguration")
@Import(AspectsBeans.class)
@EnableLoadTimeWeaving(aspectjWeaving = EnableLoadTimeWeaving.AspectJWeaving.ENABLED)
@EnableAspectJAutoProxy
public class TestsAspectsConfiguration implements LoadTimeWeavingConfigurer {

    //Logger
    private static final Logger LOGGER = LoggerFactory.getLogger(TestsAspectsConfiguration.class);

    public TestsAspectsConfiguration() {
        LOGGER.debug(SpringCommonLogging.INITIALIZATION.startInitializeConfiguration(TestsAspectsConfiguration.class));
    }

    @Override
    public LoadTimeWeaver getLoadTimeWeaver() {
        return new InstrumentationLoadTimeWeaver();
    }
}
