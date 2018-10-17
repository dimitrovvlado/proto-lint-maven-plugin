package io.github.dimitrovvlado.proto.lint.validation;

import com.google.common.base.CaseFormat;
import com.squareup.wire.schema.internal.parser.ProtoFileElement;

import java.util.List;

//TODO javadoc
public interface Validator {

    List<ValidationResult> validate(ProtoFileElement protoFile);

    default CaseFormat getCase(String s) {
        if (s.contains("_")) {
            if (s.toUpperCase().equals(s)) {
                return CaseFormat.UPPER_UNDERSCORE;
            }
            if (s.toLowerCase().equals(s)) {
                return CaseFormat.LOWER_UNDERSCORE;
            }
        } else if (s.contains("-")) {
            if (s.toLowerCase().equals(s)) {
                return CaseFormat.LOWER_HYPHEN;
            }
        } else {
            if (Character.isLowerCase(s.charAt(0))) {
                if (s.matches("([a-z]+[A-Z]+\\w+)+")) {
                    return CaseFormat.LOWER_CAMEL;
                }
            } else {
                if (s.matches("([A-Z]+[a-z]+\\w+)+")) {
                    return CaseFormat.UPPER_CAMEL;
                } else if (s.toUpperCase().equals(s)) {
                    return CaseFormat.UPPER_UNDERSCORE;
                }
            }
        }
        return null;
    }

}
