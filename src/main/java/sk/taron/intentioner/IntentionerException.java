package sk.taron.intentioner;

// TODO comments
public class IntentionerException extends RuntimeException {

    private final Integer code;
    private final IntentionerError intentionerError;

    public IntentionerException(Integer code, String message) {
        super(message);
        this.code = code;
        this.intentionerError = new IntentionerError(code, message);
    }

    public Integer getCode() {
        return code;
    }

    public IntentionerError getIntentionerError() {
        return intentionerError;
    }
}
