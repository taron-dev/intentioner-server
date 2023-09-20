package sk.taron.intentioner.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

/**
 * Represents category DTO.
 *
 * @param label category label
 * @param name  category name
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public record CategoryDTO(
    @NotBlank @JsonProperty("label") String label,
    @NotBlank @JsonProperty("name") String name,
    @Pattern(regexp = "^[{]?[0-9a-fA-F]{8}-([0-9a-fA-F]{4}-){3}[0-9a-fA-F]{12}[}]?$") @JsonProperty("categoryId") String categoryId
) {

}
