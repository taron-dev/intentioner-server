package sk.taron.intentioner;

import com.fasterxml.jackson.annotation.JsonProperty;

public record IntentionerError(
    @JsonProperty("code") Integer code,
    @JsonProperty("message") String message
){}
