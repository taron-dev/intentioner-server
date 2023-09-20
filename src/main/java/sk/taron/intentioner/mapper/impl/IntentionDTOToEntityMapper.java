package sk.taron.intentioner.mapper.impl;

import jakarta.inject.Named;
import sk.taron.intentioner.mapper.CategoryDTOToEntity;
import sk.taron.intentioner.mapper.IntentionDTOToEntity;
import sk.taron.intentioner.model.IntentionDTO;
import sk.taron.intentioner.persistence.entity.Intention;

/**
 * Mapper of {@link IntentionDTO} to {@link Intention}.
 */
@Named
public class IntentionDTOToEntityMapper implements IntentionDTOToEntity {

    private final CategoryDTOToEntity categoryDTOToEntity;

    
    /**
     * The constructor.
     * 
     * @param categoryDTOToEntity maps category DTO to entity
     */
    public IntentionDTOToEntityMapper(CategoryDTOToEntity categoryDTOToEntity) {
        this.categoryDTOToEntity = categoryDTOToEntity;
    }

    /**
    * Maps {@link IntentionDTO} to {@link Intention}.

    * @param intentionDTO the DTO to map
    * @return the mapped entity
    */
    @Override
    public Intention apply(IntentionDTO intentionDTO) {
        return new Intention(
            intentionDTO.text(),
            categoryDTOToEntity.apply(intentionDTO.category())
        );
    }
    
}
