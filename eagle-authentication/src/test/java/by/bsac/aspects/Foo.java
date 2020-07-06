package by.bsac.aspects;

import by.bsac.annotations.debug.MethodCall;
import by.bsac.annotations.debug.MethodExecutionTime;
import org.springframework.stereotype.Component;

@Component("Foo")
public class Foo {

    @MethodCall(withArgs = true, withReturnType = true, withStartTime = true)
    @MethodExecutionTime(inMicros = true)
    public void sayFoo(String msg) {
        System.out.println(msg);
    }
}
