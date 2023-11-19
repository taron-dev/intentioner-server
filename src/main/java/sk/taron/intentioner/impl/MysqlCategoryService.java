package sk.taron.intentioner.impl;

import jakarta.inject.Named;
import org.springframework.http.HttpStatus;
import sk.taron.intentioner.CategoryService;
import sk.taron.intentioner.IntentionerException;
import sk.taron.intentioner.mapper.CategoryDTOToEntity;
import sk.taron.intentioner.mapper.CategoryEntityToDTO;
import sk.taron.intentioner.model.CategoryDTO;
import sk.taron.intentioner.model.UpdateCategoryRequest;
import sk.taron.intentioner.persistence.CategoryRepository;
import sk.taron.intentioner.persistence.IntentionRepository;
import sk.taron.intentioner.persistence.entity.Category;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.BiPredicate;
import java.util.stream.Collectors;

/**
 * The category service.
 */
@Named
public class MysqlCategoryService implements CategoryService {

    private static final BiPredicate<Category, IntentionRepository> IS_CATEGORY_LINKED_TO_INTENTIONS =
        (category, intentionRepository) -> !intentionRepository.findAllByCategory(category).isEmpty();

    private final CategoryRepository categoryRepository;
    private final IntentionRepository intentionRepository;
    private final CategoryDTOToEntity categoryDTOtoEntity;
    private final CategoryEntityToDTO categoryEntityToDTO;

    /**
     * The constructor.
     *
     * @param categoryRepository  the category repository
     * @param intentionRepository the intention repository
     * @param categoryDTOtoEntity maps DTO to entity
     * @param categoryEntityToDTO maps entity to DTO
     */
    public MysqlCategoryService(
        CategoryRepository categoryRepository,
        IntentionRepository intentionRepository,
        CategoryDTOToEntity categoryDTOtoEntity,
        CategoryEntityToDTO categoryEntityToDTO
    ) {
        this.categoryRepository = categoryRepository;
        this.intentionRepository = intentionRepository;
        this.categoryDTOtoEntity = categoryDTOtoEntity;
        this.categoryEntityToDTO = categoryEntityToDTO;
    }

    /**
     * Saves new category to mysql db.
     *
     * @param categoryDTO the new category to save
     * @return created category
     */
    @Override
    public CategoryDTO saveCategory(CategoryDTO categoryDTO) {
        Category category = categoryDTOtoEntity.apply(categoryDTO);
        category = categoryRepository.saveAndFlush(category);

        return categoryEntityToDTO.apply(category);
    }

    /**
     * Get all categories.
     *
     * @return list of categories
     */
    @Override
    public List<CategoryDTO> getAllCategories() {
        return categoryRepository.findAll()
            .stream()
            .map(categoryEntityToDTO)
            .collect(Collectors.toList());
    }

    /**
     * Get category by UUID.
     *
     * @param categoryId the UUID of the category
     * @return found category
     */
    @Override
    public CategoryDTO getCategory(String categoryId) {
        UUID uuid = UUID.fromString(categoryId);

        Optional<Category> categoryDto = categoryRepository.findByCategoryId(uuid);

        return categoryDto.map(categoryEntityToDTO)
            .orElseThrow(() -> new IntentionerException(
                HttpStatus.NOT_FOUND.value(),
                "Category not found: " + categoryId
            ));
    }

    /**
     * Update existing category.
     *
     * @param categoryId            the existing category's id
     * @param updateCategoryRequest contains name and label to update
     * @return updated category
     */
    @Override
    public CategoryDTO updateCategory(String categoryId, UpdateCategoryRequest updateCategoryRequest) {
        UUID uuid = UUID.fromString(categoryId);

        Category category = categoryRepository.findByCategoryId(uuid)
            .orElseThrow(() -> new IntentionerException(
                    HttpStatus.NOT_FOUND.value(),
                    "Category with id " + categoryId + " was not found"
                )
            );

        Category updatedCategory = new Category(
            category.getId(),
            updateCategoryRequest.name(),
            updateCategoryRequest.label(),
            uuid
        );

        updatedCategory = categoryRepository.saveAndFlush(updatedCategory);

        return categoryEntityToDTO.apply(updatedCategory);
    }

    /**
     * Delete category.
     *
     * @param categoryId the id of category to delete
     */
    @Override
    public void deleteCategory(String categoryId) {
        UUID uuid = UUID.fromString(categoryId);
        Category category = categoryRepository.findByCategoryId(uuid)
            .orElseThrow(() -> new IntentionerException(
                    HttpStatus.NOT_FOUND.value(),
                    "Category with id " + categoryId + " was not found"
                )
            );

        if(IS_CATEGORY_LINKED_TO_INTENTIONS.test(category, intentionRepository)){
            throw new IntentionerException(HttpStatus.CONFLICT.value(), "Category contains intentions");
        }

        categoryRepository.deleteById(category.getId());
    }
}
