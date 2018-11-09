package io.github.dimitrovvlado.proto.lint.validation;

import com.squareup.wire.schema.Location;
import com.squareup.wire.schema.internal.parser.ProtoFileElement;
import com.squareup.wire.schema.internal.parser.ProtoParser;
import okio.Okio;
import okio.Source;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * A facade for invoking all the validators
 */
public class ValidatorService {

    private Map<String, Validator> validators = new HashMap<>();

    private static ValidatorService INSTANCE = new ValidatorService();

    static {
        INSTANCE.addValidator("messages", new MessagesValidator());
        INSTANCE.addValidator("services", new ServicesValidator());
    }

    public static ValidatorService getInstance() {
        return INSTANCE;
    }

    public void addValidator(String name, Validator validator) {
        validators.put(name, validator);
    }

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
