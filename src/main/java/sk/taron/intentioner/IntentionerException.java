package sk.taron.intentioner;

import java.util.List;

public class IntentionerException extends RuntimeException {

    private final Integer code;
    private final List<String> errors;
    private IntentionerError intentionerError;

    public IntentionerException(Integer code, String message) {
        super(message);
        this.code = code;
        this.errors = List.of();
    }

    public IntentionerException(Integer code, String message, List<String> errors) {
        super(message);
        this.code = code;
        this.errors = errors;
    }

    public IntentionerException(Integer code, IntentionerError intentionerError) {
        super(intentionerError.message());
        this.errors = List.of();
        this.code = code;
        this.intentionerError = intentionerError;
    }

    public Integer getCode() {
        return code;
    }

    public List<String> getErrors() {
        return errors;
    }

    public IntentionerError getIntentionerError() {
        return intentionerError;
    }
}
