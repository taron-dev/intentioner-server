package sk.taron.intentioner.model;

public record UpdateIntentionRequest(
    String text,
    CategoryDTO category
) {
}
