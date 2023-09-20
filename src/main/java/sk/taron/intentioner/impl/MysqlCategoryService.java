package sk.taron.intentioner.impl;

import jakarta.inject.Named;
import sk.taron.intentioner.CategoryService;
import sk.taron.intentioner.IntentionerException;
import sk.taron.intentioner.mapper.CategoryDTOToEntity;
import sk.taron.intentioner.mapper.CategoryEntityToDTO;
import sk.taron.intentioner.model.CategoryDTO;
import sk.taron.intentioner.model.UpdateCategoryRequest;
import sk.taron.intentioner.persistence.entity.Category;
import sk.taron.intentioner.persistence.CategoryRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Named
public class MysqlCategoryService implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryDTOToEntity categoryDTOtoEntity;
    private final CategoryEntityToDTO categoryEntityToDTO;

    public MysqlCategoryService(
        CategoryRepository categoryRepository,
        CategoryDTOToEntity categoryDTOtoEntity,
        CategoryEntityToDTO categoryEntityToDTO
    ) {
        this.categoryRepository = categoryRepository;
        this.categoryDTOtoEntity = categoryDTOtoEntity;
        this.categoryEntityToDTO = categoryEntityToDTO;
    }

    @Override
    public CategoryDTO saveCategory(CategoryDTO categoryDTO) {
        Category category = categoryDTOtoEntity.apply(categoryDTO);
        category = categoryRepository.saveAndFlush(category);

        return categoryEntityToDTO.apply(category);
    }

    @Override
    public List<CategoryDTO> getAllCategories() {
        return categoryRepository.findAll()
            .stream()
            .map(categoryEntityToDTO)
            .collect(Collectors.toList());
    }

    @Override
    public CategoryDTO getCategory(String categoryId) {
        UUID uuid = UUID.fromString(categoryId);

        Optional<Category> categoryDto = categoryRepository.findByCategoryId(uuid);

        return categoryDto.map(categoryEntityToDTO).orElseThrow();
    }

    @Override
    public CategoryDTO updateCategory(String categoryId, UpdateCategoryRequest updateCategoryRequest) {
        UUID uuid = UUID.fromString(categoryId);

        Category category = categoryRepository.findByCategoryId(uuid)
            .orElseThrow(() -> new IntentionerException(404, "Category with id " + categoryId + " was not found"));

        Category updatedCategory = new Category(
            category.getId(),
            updateCategoryRequest.name(),
            updateCategoryRequest.label(),
            uuid
        );

        updatedCategory = categoryRepository.saveAndFlush(updatedCategory);

        return categoryEntityToDTO.apply(updatedCategory);
    }

    @Override
    public void deleteCategory(String categoryId) {
        UUID uuid = UUID.fromString(categoryId);
        Category category = categoryRepository.findByCategoryId(uuid)
            .orElseThrow(() -> new IntentionerException(404, "Category with id " + categoryId + " was not found"));

        categoryRepository.deleteById(category.getId());
    }
}
