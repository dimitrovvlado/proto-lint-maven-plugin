package io.github.dimitrovvlado.proto.lint.validation;

import com.squareup.wire.schema.Location;
import com.squareup.wire.schema.internal.parser.ProtoFileElement;
import com.squareup.wire.schema.internal.parser.ProtoParser;
import okio.Okio;
import okio.Source;
import org.junit.Before;

import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;

public abstract class AbstractValidatorTest {

    protected MessagesValidator messagesValidator;
    protected ServicesValidator servicesValidator;

    @Before
    public void setup() {
        messagesValidator = new MessagesValidator();
        servicesValidator = new ServicesValidator();
    }

    protected ProtoFileElement protoFile(String fileName) throws IOException {
        URL url = MessagesValidatorTest.class.getResource(fileName);
        Path path = Paths.get(URI.create(url.toString()));
        try (Source source = Okio.source(path)) {
            Location location = Location.get(path.toFile().getAbsolutePath());
            String data = Okio.buffer(source).readUtf8();
            return ProtoParser.parse(location, data);
        }
    }
}
