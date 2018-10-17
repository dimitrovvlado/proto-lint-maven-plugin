package io.github.dimitrovvlado.proto.lint.validation;

import org.apache.maven.plugin.logging.Log;

//TODO javadoc
public class ValidationResult {

    public enum Severity {
        DEBUG, ERROR
    }

    private final Severity severity;
    private final String message;

    public ValidationResult(Severity severity, String message) {
        this.severity = severity;
        this.message = message;
    }

    public Severity getSeverity() {
        return severity;
    }

    public String getMessage() {
        return message;
    }

    /**
     * @return number of validation errors.
     */
    public int apply(Log log) {
        if (Severity.DEBUG.equals(severity)) {
            if (log.isDebugEnabled()) {
                log.debug(message);
            }
            return 0;
        } else if (Severity.ERROR.equals(severity)) {
            log.error(message);
            return 1;
        } else {
            log.error("Unexpected severity " + severity);
            return 1;
        }
    }

}
