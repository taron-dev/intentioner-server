package sk.taron.intentioner;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record IntentionerError(
    @NotBlank @JsonProperty("code") Integer code,
    @NotBlank @JsonProperty("message") String message
){}
