package sk.taron.intentioner.mapper;

import sk.taron.intentioner.model.CategoryDTO;
import sk.taron.intentioner.persistence.entity.Category;

import java.util.function.Function;

/**
 * Maps {@link CategoryDTO} to {@link Category}.
 */
public interface CategoryEntityToDTO extends Function<Category, CategoryDTO> {
}
