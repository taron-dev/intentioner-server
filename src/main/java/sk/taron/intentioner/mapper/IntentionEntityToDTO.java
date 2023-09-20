package sk.taron.intentioner.mapper;

import java.util.function.Function;

import sk.taron.intentioner.model.IntentionDTO;
import sk.taron.intentioner.persistence.entity.Intention;

/**
 * Maps {@link Intention} to {@link IntentionDTO}.
 */
public interface IntentionEntityToDTO extends Function<Intention, IntentionDTO> {
    
}
