package sk.taron.intentioner.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;

public record UpdateCategoryRequest(
    @NotBlank @JsonProperty("label") String label,
    @NotBlank @JsonProperty("name") String name
) {
}
