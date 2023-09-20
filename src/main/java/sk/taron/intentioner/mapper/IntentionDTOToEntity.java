package sk.taron.intentioner.mapper;

import java.util.function.Function;

import sk.taron.intentioner.model.IntentionDTO;
import sk.taron.intentioner.persistence.entity.Intention;

/**
 * Maps {@link IntentionDTO} to {@link Intention}.
 */
public interface IntentionDTOToEntity extends Function<IntentionDTO, Intention> {
    
}
