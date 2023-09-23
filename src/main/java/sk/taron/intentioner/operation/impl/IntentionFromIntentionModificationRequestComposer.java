package sk.taron.intentioner.operation.impl;

import jakarta.inject.Named;
import sk.taron.intentioner.model.IntentionModificationRequest;
import sk.taron.intentioner.operation.IntentionFromIntentionModificationRequest;
import sk.taron.intentioner.persistence.CategoryRepository;
import sk.taron.intentioner.persistence.entity.Intention;

import java.util.Optional;
import java.util.UUID;

/**
 * Compose {@link Intention} from {@link IntentionModificationRequest}.
 */
@Named
public class IntentionFromIntentionModificationRequestComposer implements IntentionFromIntentionModificationRequest {

    private final CategoryRepository categoryRepository;

    /**
     * The constructor.
     *
     * @param categoryRepository the category repository
     */
    public IntentionFromIntentionModificationRequestComposer(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    /**
     * Compose intention via finding category by id and use text from input.
     *
     * @param intentionModificationRequest contains data required for modification
     * @return composed intention or optional empty if category was not found
     */
    @Override
    public Optional<Intention> apply(IntentionModificationRequest intentionModificationRequest) {
        UUID categoryId = UUID.fromString(intentionModificationRequest.categoryId());

        return categoryRepository.findByCategoryId(categoryId)
            .map(category -> new Intention(intentionModificationRequest.text(), category));
    }
}
