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
        assertEquals(3, result.size());
        assertThat(result, contains(
                hasProperty("message", is("Field name 'exampleId' in message 'CreateOrUpdateExampleRequest' is not in lower underscore case.")),
                hasProperty("message", is("Field name 'ExampleId' in message 'CreateOrUpdateExampleResponse' is not in lower underscore case.")),
                hasProperty("message", is("Field name 'total_Count' in message 'CreateOrUpdateExampleResponse' is not in lower underscore case."))
        ));
    }

    @Test
    public void testInvalidEnum() throws Exception {
        List<ValidationResult> result = messagesValidator.validate(protoFile("/invalidEnum.proto"));
        assertNotNull(result);
        assertEquals(4, result.size());
        assertThat(result, contains(
                hasProperty("message", is("Message name 'color' is not in upper camel case.")),
                hasProperty("message", is("Constant name 'Red' in message 'color' is not in upper underscore case.")),
                hasProperty("message", is("Constant name 'blue' in message 'color' is not in upper underscore case.")),
                hasProperty("message", is("Constant name 'DARK-GREEN' in message 'color' is not in upper underscore case."))
        ));
    }

    @Test
    public void testInvalidInnerFields() throws Exception {
        List<ValidationResult> result = messagesValidator.validate(protoFile("/invalidNestedFields.proto"));
        assertNotNull(result);
        assertEquals(5, result.size());
        assertThat(result, contains(
                hasProperty("message", is("Field name 'exampleId' in message 'InnerRequestData' is not in lower underscore case.")),
                hasProperty("message", is("Constant name 'red' in message 'Color' is not in upper underscore case.")),
                hasProperty("message", is("Field name 'exampleString' in message 'InnerInnerRequestData' is not in lower underscore case.")),
                hasProperty("message", is("Field name 'ExampleId' in message 'InnerResponseData' is not in lower underscore case.")),
                hasProperty("message", is("Field name 'total_Count' in message 'InnerResponseData' is not in lower underscore case."))
        ));
    }

    @Test
    public void testInvalidInnerMessageNames() throws Exception {
        List<ValidationResult> result = messagesValidator.validate(protoFile("/invalidNestedMessages.proto"));
        assertNotNull(result);
        assertEquals(3, result.size());
        assertThat(result, contains(
                hasProperty("message", is("Message name 'innerRequestData' is not in upper camel case.")),
                hasProperty("message", is("Message name 'COLOR' is not in upper camel case.")),
                hasProperty("message", is("Message name 'inner_response_Data' is not in upper camel case."))
        ));
    }
}
