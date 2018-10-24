package io.github.dimitrovvlado.proto.lint.validation;

import org.junit.Test;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class MessagesValidatorTest extends AbstractValidatorTest {

    @Test
    public void testValidMessages() throws Exception {
        List<ValidationResult> result = messagesValidator.validate(protoFile("/valid.proto"));
        assertNotNull(result);
        assertEquals(0, result.size());
    }

    @Test
    public void testInvalidMessageNames() throws Exception {
        List<ValidationResult> result = messagesValidator.validate(protoFile("/invalidMessage.proto"));
        assertNotNull(result);
        assertEquals(2, result.size());
        assertThat(result, contains(
                hasProperty("message", is("Message name 'createOrUpdateExampleRequest' is not in upper camel case.")),
                hasProperty("message", is("Message name 'create_or_update_example_response' is not in upper camel case."))
        ));
    }

    @Test
    public void testInvalidFieldNames() throws Exception {
        List<ValidationResult> result = messagesValidator.validate(protoFile("/invalidFields.proto"));
        assertNotNull(result);
        assertEquals(2, result.size());
        assertThat(result, contains(
                hasProperty("message", is("Field name 'exampleId' in message 'CreateOrUpdateExampleRequest' is not in lower underscore case.")),
                hasProperty("message", is("Field name 'ExampleId' in message 'CreateOrUpdateExampleResponse' is not in lower underscore case."))
        ));
    }

    @Test
    public void testInvaliEnum() throws Exception {
        List<ValidationResult> result = messagesValidator.validate(protoFile("/invalidEnum.proto"));
        assertNotNull(result);
        assertEquals(3, result.size());
        assertThat(result, contains(
                hasProperty("message", is("Message name 'color' is not in upper camel case.")),
                hasProperty("message", is("Constant name 'Red' in message 'color' is not in upper underscore case.")),
                hasProperty("message", is("Constant name 'blue' in message 'color' is not in upper underscore case."))
        ));
    }

}
