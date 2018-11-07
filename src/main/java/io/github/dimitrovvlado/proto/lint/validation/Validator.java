package io.github.dimitrovvlado.proto.lint.validation;

import com.squareup.wire.schema.internal.parser.ProtoFileElement;

import java.util.List;

/**
 * Interface for validating elements of a proto file.
 */
public interface Validator {

    /**
     * Validates a certain category of proto file elements and returns a list of validation results.
     *
     * @param protoFile the parsed proto file, cannot be null.
     * @return a list of validation errors, empty list if no validation errors were found.
     */
    List<ValidationResult> validate(ProtoFileElement protoFile);

}
