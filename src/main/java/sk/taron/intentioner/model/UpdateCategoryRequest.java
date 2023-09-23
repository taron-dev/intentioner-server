package sk.taron.intentioner.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;

/**
 * Contains data to which is category going to be updated.
 *
 * @param label the category's label
 * @param name the category's name
 */
public record UpdateCategoryRequest(
    @NotBlank @JsonProperty("label") String label,
    @NotBlank @JsonProperty("name") String name
) {
}
