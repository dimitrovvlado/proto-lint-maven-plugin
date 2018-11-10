package io.github.dimitrovvlado.proto.lint.validation;

import com.google.common.collect.ImmutableList;
import com.squareup.wire.schema.internal.parser.EnumConstantElement;
import com.squareup.wire.schema.internal.parser.EnumElement;
import com.squareup.wire.schema.internal.parser.FieldElement;
import com.squareup.wire.schema.internal.parser.MessageElement;
import com.squareup.wire.schema.internal.parser.ProtoFileElement;
import com.squareup.wire.schema.internal.parser.TypeElement;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import static io.github.dimitrovvlado.proto.lint.validation.CaseFormat.*;

public class MessagesValidator implements Validator {

    @Override
    public List<ValidationResult> validate(ProtoFileElement protoFile) {
        List<ValidationResult> result = new LinkedList<>();
        for (TypeElement typeElement : protoFile.types()) {
            processType(typeElement, result);
            //Process inner types
            Queue<ImmutableList<TypeElement>> queue = new LinkedList<>();
            queue.add(typeElement.nestedTypes());
            while (!queue.isEmpty()) {
                for (TypeElement nestedElement : queue.remove()) {
                    processType(nestedElement, result);
                    queue.add(nestedElement.nestedTypes());
                }
            }
        }
        return result;
    }

    private void processType(TypeElement typeElement, List<ValidationResult> result) {
        if (!UPPER_CAMEL.equals(getCase(typeElement.name()))) {
            result.add(new ValidationResult(ValidationResult.Severity.ERROR,
                    String.format("Message name '%s' is not in upper camel case.", typeElement.name())));
        }
        if (typeElement instanceof MessageElement) {
            MessageElement element = (MessageElement)typeElement;
            for (FieldElement field : element.fields()) {
                CaseFormat caseFormat = getCase(field.name());
                if (!LOWER_UNDERSCORE.equals(caseFormat) && !ALL_LOWER.equals(caseFormat)) {
                    result.add(new ValidationResult(ValidationResult.Severity.ERROR,
                            String.format("Field name '%s' in message '%s' is not in lower underscore case.", field.name(), element.name())));
                }
            }
        } else if (typeElement instanceof EnumElement) {
            EnumElement element = (EnumElement)typeElement;
            for (EnumConstantElement constant : element.constants()) {
                CaseFormat caseFormat = getCase(constant.name());
                if (!UPPER_UNDERSCORE.equals(caseFormat) && !ALL_UPPER.equals(caseFormat)) {
                    result.add(new ValidationResult(ValidationResult.Severity.ERROR,
                            String.format("Constant name '%s' in message '%s' is not in upper underscore case.", constant.name(), element.name())));
                }
            }
        }
    }

    @Override
    public String getName() {
        return "messagesValidator";
    }
}
