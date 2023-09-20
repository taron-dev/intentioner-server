package sk.taron.intentioner.mapper.impl;

import jakarta.inject.Named;
import sk.taron.intentioner.mapper.CategoryDTOToEntity;
import sk.taron.intentioner.model.CategoryDTO;
import sk.taron.intentioner.persistence.entity.Category;

/**
 * Mapper of {@link CategoryDTO} to {@link Category}.
 */
@Named
public class CategoryDTOToEntityMapper implements CategoryDTOToEntity {

    /**
     * Maps {@link CategoryDTO} to {@link Category}.
     * 
     * @param categoryDTO the DTO to map
     * @return the mapped entity
     */
    @Override
    public Category apply(CategoryDTO categoryDTO) {
        return new Category(categoryDTO.name(), categoryDTO.label());
    }
}
