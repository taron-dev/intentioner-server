package sk.taron.intentioner.model;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record IntentionDTO(
    @NotBlank @JsonProperty("text") String text,
    @Pattern(regexp = "^[{]?[0-9a-fA-F]{8}-([0-9a-fA-F]{4}-){3}[0-9a-fA-F]{12}[}]?$") @JsonProperty("intentionId") UUID intentionId,
    @NotNull @JsonProperty("category") CategoryDTO category
) {
}
