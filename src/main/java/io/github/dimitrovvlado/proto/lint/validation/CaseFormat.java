package io.github.dimitrovvlado.proto.lint.validation;

public enum CaseFormat {

    UPPER_UNDERSCORE, LOWER_UNDERSCORE, LOWER_HYPHEN, LOWER_CAMEL, UPPER_CAMEL, ALL_LOWER, ALL_UPPER;

    public static CaseFormat getCase(String s) {
        if (s.contains("_")) {
            if (s.toUpperCase().equals(s)) {
                return UPPER_UNDERSCORE;
            }
            if (s.toLowerCase().equals(s)) {
                return LOWER_UNDERSCORE;
            }
        } else if (s.contains("-")) {
            if (s.toLowerCase().equals(s)) {
                return LOWER_HYPHEN;
            }
        } else {
            if (Character.isLowerCase(s.charAt(0))) {
                if (s.matches("([a-z0-9]+[A-Z]+[a-z0-9]*)+")) {
                    return LOWER_CAMEL;
                } else if (s.matches("([a-z]+[a-z0-9]*)")) {
                    return ALL_LOWER;
                }
            } else {
                if (s.matches("([A-Z]+[a-z]+\\w+)+")) {
                    return UPPER_CAMEL;
                } else if (s.matches("([A-Z]+[A-Z0-9]*)")) {
                    return ALL_UPPER;
                }
            }
        }
        return null;
    }
    }
