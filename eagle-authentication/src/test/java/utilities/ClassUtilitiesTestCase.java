package utilities;

import by.bsac.utilities.ClazzUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ClassUtilitiesTestCase {

    @Test
    void isPrimitiveWrapper_primitiveWrapper_shouldReturnTrue() {
        Assertions.assertTrue(ClazzUtils.isPrimitiveWrapper(Double.class));
    }

    @Test
    void isPrimitiveWrapper_notPrimitiveWrapper_shouldReturnFalse() {
        Assertions.assertFalse(ClazzUtils.isPrimitiveWrapper(String.class));
    }

    @Test
    void isPrimitiveWrapper_nullClass_shouldThrowNPE() {
        Assertions.assertThrows(NullPointerException.class, () -> ClazzUtils.isPrimitiveWrapper(null))
;    }
}
