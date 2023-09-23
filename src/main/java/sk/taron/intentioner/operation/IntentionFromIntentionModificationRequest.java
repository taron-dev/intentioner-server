package sk.taron.intentioner.operation;

import sk.taron.intentioner.model.IntentionModificationRequest;
import sk.taron.intentioner.persistence.entity.Intention;

import java.util.Optional;
import java.util.function.Function;

/**
 * Compose {@link Intention} from {@link IntentionModificationRequest}.
 */
public interface IntentionFromIntentionModificationRequest extends Function<IntentionModificationRequest, Optional<Intention>> {
}
