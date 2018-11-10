package io.github.dimitrovvlado.proto.lint.validation;

import com.squareup.wire.schema.Location;
import com.squareup.wire.schema.internal.parser.ProtoFileElement;
import com.squareup.wire.schema.internal.parser.ProtoParser;
import okio.Okio;
import okio.Source;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * A facade for invoking all the validators
 */
public class ValidatorService {

    private Map<String, Validator> validators = new HashMap<>();

    /**
     * Constructs a {@link ValidatorService} instance with default set of validators
     *
     * @return a new ValidatorInstance
     */
    public static ValidatorService getInstance() {
        ValidatorService instance = new ValidatorService();
        instance.includeValidator(new MessagesValidator());
        instance.includeValidator(new ServicesValidator());
        return instance;
    }

    /**
     * Includes a validator to the {@link ValidatorService}
     *
     * @param validator the {@link Validator} implementation to include.
     */
    public void includeValidator(Validator validator) {
        validators.put(validator.getName(), validator);
    }

    /**
     * Exclude a validator from the {@link ValidatorService}. Useful for excluding default validations.
     *
     * @param name the name of the validator to exclude.
     */
    public void excludeValidator(String name) {
        validators.remove(name);
    }

    public Map<String, Validator> getValidators() {
        return Collections.unmodifiableMap(validators);
    }

    /**
     * Applies all the validation rules on the provided .proto file.
     *
     * @param file the proto file to validate.
     * @return a list of validation errors if any.
     * @throws IOException if the provided validation file cannot be open,
     */
    public List<ValidationResult> apply(File file) throws IOException {
        try (Source source = Okio.source(file)) {
            Location location = Location.get(file.getAbsolutePath());
            String data = Okio.buffer(source).readUtf8();
            ProtoFileElement protoFile = ProtoParser.parse(location, data);
            return validators.entrySet().stream().
                    map(v -> v.getValue().validate(protoFile)).
                    flatMap(Collection::stream).
                    collect(Collectors.toList());
        } catch (IOException e) {
            throw new IOException("Failed to load " + file, e);
        }
    }
}
