package sk.taron.intentioner.mvc;

import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import sk.taron.intentioner.IntentionerError;
import sk.taron.intentioner.IntentionerException;

@RestControllerAdvice
public class CategoryControllerAdvice {

    @ExceptionHandler(IntentionerException.class)
    public ResponseEntity<IntentionerError> handleIntentionerException(IntentionerException intentionerException){
        switch (intentionerException.getCode()) {
            case 404: {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(intentionerException.getIntentionerError());
            }
            case 409: {
                return ResponseEntity.status(HttpStatus.CONFLICT).body(intentionerException.getIntentionerError());
            }
            default: return ResponseEntity.internalServerError().build();
        }
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<IntentionerError> handleConstrainViolationException(ConstraintViolationException constraintViolationException){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new IntentionerError(HttpStatus.BAD_REQUEST.value(), constraintViolationException.getMessage()));
    }
}
