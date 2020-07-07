package by.bsac.configuration.noscan;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

import static by.bsac.core.logging.SpringCommonLogging.*;

@Configuration("NoComponentScanConfiguration")
public class NoScan {

    //Logger
    private static final Logger LOGGER = LoggerFactory.getLogger(NoScan.class);

    public NoScan() {
        LOGGER.info(INITIALIZATION.startInitializeConfiguration(NoScan.class));
        LOGGER.warn("Disable Spring boot component scanning;");
    }
}
