package io.github.dimitrovvlado.proto.lint.validation;

import com.squareup.wire.schema.Location;
import com.squareup.wire.schema.internal.parser.ProtoFileElement;
import com.squareup.wire.schema.internal.parser.ProtoParser;
import okio.Okio;
import okio.Source;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

//TODO javadoc
public class ValidatorService {

    private Set<Validator> validators = new HashSet<>();

    private static ValidatorService INSTANCE = new ValidatorService();

    public static ValidatorService getInstance() {
        INSTANCE.addValidator(new MessagesValidator());
        INSTANCE.addValidator(new ServicesValidator());
        return INSTANCE;
    }

    public void addValidator(Validator validator) {
        validators.add(validator);
    }

    public List<ValidationResult> apply(File file) throws IOException {
        try (Source source = Okio.source(file)) {
            Location location = Location.get(file.getAbsolutePath());
            String data = Okio.buffer(source).readUtf8();
            ProtoFileElement protoFile = ProtoParser.parse(location, data);
            return validators.stream().map(v -> v.validate(protoFile)).flatMap(Collection::stream).collect(Collectors.toList());
        } catch (IOException e) {
            throw new IOException("Failed to load " + file, e);
        }
    }
}
