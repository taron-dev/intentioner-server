package sk.taron.intentioner.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

/**
 * The model for creating and updating intention.
 *
 * @param text       the text of the intention
 * @param categoryId the category id of the intention
 */
public record IntentionModificationRequest(
    @NotBlank @JsonProperty("text") String text,
    @NotBlank @Pattern(regexp = "^[{]?[0-9a-fA-F]{8}-([0-9a-fA-F]{4}-){3}[0-9a-fA-F]{12}[}]?$") @JsonProperty("categoryId") String categoryId
) {
}
