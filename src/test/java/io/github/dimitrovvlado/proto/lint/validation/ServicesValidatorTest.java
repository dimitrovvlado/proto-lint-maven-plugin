package io.github.dimitrovvlado.proto.lint.validation;

import org.junit.Test;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class ServicesValidatorTest extends AbstractValidatorTest {

    @Test
    public void testInvalidService() throws Exception {
        List<ValidationResult> result = servicesValidator.validate(protoFile("/invalidService.proto"));
        assertNotNull(result);
        assertEquals(2, result.size());
        assertThat(result, contains(
                hasProperty("message", is("Service name 'exampleService' is not in upper camel case.")),
                hasProperty("message", is("Service name 'example_service' is not in upper camel case."))
        ));
    }

    @Test
    public void testInvalidRpc() throws Exception {
        List<ValidationResult> result = servicesValidator.validate(protoFile("/invalidRpc.proto"));
        assertNotNull(result);
        assertEquals(2, result.size());
        assertThat(result, contains(
                hasProperty("message", is("RPC name 'ExampleService' in service 'createOrUpdateExample' is not in upper camel case.")),
                hasProperty("message", is("RPC name 'ExampleService' in service 'create_or_update_example' is not in upper camel case."))
        ));
    }

}
