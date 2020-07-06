package by.bsac.aspects;

import by.bsac.annotations.debug.MethodCall;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@ActiveProfiles("ASPECTS_DEBUG")
@SpringJUnitConfig(classes = {TestsAspectsConfiguration.class, BeansConfiguration.class})
public class FooIntegrationTest {

    @Autowired
    private Foo foo;

    @Test
    @MethodCall
    void sayFoo_annotatedMethod_shouldDisplayAspectsLogBeforeAndAfter() {
        foo.sayFoo("Hello from foo!");
    }
}
