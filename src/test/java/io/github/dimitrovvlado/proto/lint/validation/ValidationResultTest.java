package io.github.dimitrovvlado.proto.lint.validation;

import org.apache.maven.plugin.testing.SilentLog;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ValidationResultTest {

    @Test
    public void validateErrorCount() {
        ValidationResult result = new ValidationResult(ValidationResult.Severity.ERROR, "message");
        assertEquals(1, result.apply(new SilentLog()));

        result = new ValidationResult(ValidationResult.Severity.WARN, "message");
        assertEquals(0, result.apply(new SilentLog()));
    }

    @Test(expected = NullPointerException.class)
    public void testNullSeverity() {
        new ValidationResult(null, "message");
    }

    @Test(expected = NullPointerException.class)
    public void testNullMessage() {
        new ValidationResult(ValidationResult.Severity.ERROR, null);
    }
}
