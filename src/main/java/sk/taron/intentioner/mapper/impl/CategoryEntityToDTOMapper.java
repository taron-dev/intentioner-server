package sk.taron.intentioner.mapper.impl;

import jakarta.inject.Named;
import sk.taron.intentioner.mapper.CategoryEntityToDTO;
import sk.taron.intentioner.model.CategoryDTO;
import sk.taron.intentioner.persistence.entity.Category;

/**
 * Mapper of {@link Category} to {@link CategoryDTO}.
 */
@Named
public class CategoryEntityToDTOMapper implements CategoryEntityToDTO {

    /**
     * Maps {@link Category} to {@link CategoryDTO}.
     * 
     * @param category the entity to map
     * @return the mapped DTO
     */
    @Override
    public CategoryDTO apply(Category category) {
        return new CategoryDTO(
            category.getLabel(),
            category.getName(),
            category.getCategoryId().toString()
        );
    }
}
