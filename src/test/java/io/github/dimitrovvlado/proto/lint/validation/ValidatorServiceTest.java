package io.github.dimitrovvlado.proto.lint.validation;

import com.squareup.wire.schema.internal.parser.ProtoFileElement;
import org.junit.Test;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Tests for {@link ValidatorService} class
 */
public class ValidatorServiceTest {

    @Test
    public void instantiateAndExclude() {
        ValidatorService instance = ValidatorService.getInstance();
        assertNotNull(instance);
        Map<String, Validator> validators = instance.getValidators();
        assertNotNull(validators);
        assertEquals(2, validators.size()); //Expected 2 default validators
        assertTrue(validators.get("messagesValidator") instanceof MessagesValidator);
        assertTrue(validators.get("servicesValidator") instanceof ServicesValidator);

        instance.excludeValidator("servicesValidator");
        validators = instance.getValidators();
        assertNotNull(validators);
        assertEquals(1, validators.size());
        assertTrue(validators.get("messagesValidator") instanceof MessagesValidator);

        instance.excludeValidator("messagesValidator");
        validators = instance.getValidators();
        assertNotNull(validators);
        assertEquals(0, validators.size());
    }

    @Test
    public void instantiateAndInclude() {
        ValidatorService instance = ValidatorService.getInstance();
        assertNotNull(instance);
        Map<String, Validator> validators = instance.getValidators();
        assertNotNull(validators);
        assertEquals(2, validators.size()); //Expected 2 default validators

        instance.includeValidator(new Validator() {
            @Override
            public List<ValidationResult> validate(ProtoFileElement protoFile) {
                return null;
            }

            @Override
            public String getName() {
                return "foobar";
            }
        });

        validators = instance.getValidators();
        assertNotNull(validators);
        assertEquals(3, validators.size());
    }
}
