package sk.taron.intentioner;

import sk.taron.intentioner.model.CategoryDTO;
import sk.taron.intentioner.model.UpdateCategoryRequest;

import java.util.List;

/**
 * Provides operations over category.
 */
public interface CategoryService {

    /**
     * Saves new category to mysql db.
     *
     * @param categoryDTO the new category to save
     * @return created category
     */
    CategoryDTO saveCategory(CategoryDTO categoryDTO);

    /**
     * Get all categories.
     *
     * @return list of categories
     */
    List<CategoryDTO> getAllCategories();

    /**
     * Get category by UUID.
     *
     * @param categoryUUID the UUID of the category
     * @return found category
     */
    CategoryDTO getCategory(String categoryUUID);

    /**
     * Update existing category.
     *
     * @param categoryId the existing category's id
     * @param updateCategoryRequest contains name and label to update
     * @return updated category
     */
    CategoryDTO updateCategory(String categoryId, UpdateCategoryRequest updateCategoryRequest);

    /**
     * Delete category.
     *
     * @param categoryId the id of category to delete
     */
    void deleteCategory(String categoryId);
}
