package io.github.dimitrovvlado.proto.lint.validation;

import com.squareup.wire.schema.Location;
import com.squareup.wire.schema.internal.parser.ProtoFileElement;
import com.squareup.wire.schema.internal.parser.ProtoParser;
import okio.Okio;
import okio.Source;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class MessagesValidatorTest {

    private MessagesValidator validator;

    @Before
    public void setup() {
        validator = new MessagesValidator();
    }

    @Test
    public void testValidMessages() throws Exception {
        List<ValidationResult> result = validator.validate(protoFile("/valid.proto"));
        assertNotNull(result);
        assertEquals(0, result.size());
    }

    private ProtoFileElement protoFile(String fileName) throws Exception {
        URL url = MessagesValidatorTest.class.getResource(fileName);
        Path path = Paths.get(url.toURI());
        try (Source source = Okio.source(path)) {
            Location location = Location.get(path.toFile().getAbsolutePath());
            String data = Okio.buffer(source).readUtf8();
            return ProtoParser.parse(location, data);
        } catch (IOException e) {
            throw new IOException("Failed to load " + fileName, e);
        }
    }
}
