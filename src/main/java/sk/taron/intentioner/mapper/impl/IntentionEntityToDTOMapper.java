package sk.taron.intentioner.mapper.impl;

import jakarta.inject.Named;
import sk.taron.intentioner.mapper.CategoryEntityToDTO;
import sk.taron.intentioner.mapper.IntentionEntityToDTO;
import sk.taron.intentioner.model.IntentionDTO;
import sk.taron.intentioner.persistence.entity.Intention;

/**
 * Mapper of {@link Intention} to {@link IntentionDTO}.
 */
@Named
public class IntentionEntityToDTOMapper implements IntentionEntityToDTO {

    private final CategoryEntityToDTO categoryEntityToDTO;
    
    /**
     * The constructor.
     * 
     * @param categoryEntityToDTO maps category entity to DTO
     */
    public IntentionEntityToDTOMapper(CategoryEntityToDTO categoryEntityToDTO) {
        this.categoryEntityToDTO = categoryEntityToDTO;
    }

    /**
    * Maps {@link Intention} to {@link IntentionDTO}.
    
    * @param intention the intention entity to map
    * @return the mapped DTO
    */
    @Override
    public IntentionDTO apply(Intention intention) {
        return new IntentionDTO(
            intention.getText(),
            intention.getIntentionId(), 
            categoryEntityToDTO.apply(intention.getCategory())
        );
    }

}
