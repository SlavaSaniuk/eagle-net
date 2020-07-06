package configuration;

import by.bsac.conf.RootContextConfiguration;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class RootContextConfigurationTestCase {

    private RootContextConfiguration root_ctx;

    @Test
    void display_initialization_log() {
        this.root_ctx = new RootContextConfiguration();
        Assertions.assertTrue(true);
    }
}
