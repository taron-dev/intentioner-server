package sk.taron.intentioner.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import sk.taron.intentioner.CategoryService;
import sk.taron.intentioner.IntentionerException;
import sk.taron.intentioner.mapper.CategoryDTOToEntity;
import sk.taron.intentioner.mapper.CategoryEntityToDTO;
import sk.taron.intentioner.persistence.CategoryRepository;
import sk.taron.intentioner.persistence.IntentionRepository;
import sk.taron.intentioner.persistence.entity.Category;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static sk.taron.intentioner.CategoryDataProvider.CATEGORY;
import static sk.taron.intentioner.CategoryDataProvider.CATEGORY_DTO;
import static sk.taron.intentioner.CategoryDataProvider.CATEGORY_ID;
import static sk.taron.intentioner.CategoryDataProvider.CATEGORY_UUID;
import static sk.taron.intentioner.CategoryDataProvider.UPDATE_CATEGORY_REQUEST;
import static sk.taron.intentioner.IntentionDataProvider.INTENTION;

/**
 * Tests for {@link CategoryService} implemented by {@link MysqlCategoryService}.
 */
@ExtendWith(MockitoExtension.class)
class MysqlCategoryServiceTest {

    private static final Category UPDATED_CATEGORY = new Category(
        CATEGORY.getId(),
        UPDATE_CATEGORY_REQUEST.name(),
        UPDATE_CATEGORY_REQUEST.label(),
        CATEGORY_UUID
    );

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private IntentionRepository intentionRepository;

    @Mock
    private CategoryDTOToEntity categoryDTOtoEntity;

    @Mock
    private CategoryEntityToDTO categoryEntityToDTO;

    private CategoryService categoryService;

    @BeforeEach
    void setUp() {
        this.categoryService = new MysqlCategoryService(
            categoryRepository,
            intentionRepository,
            categoryDTOtoEntity,
            categoryEntityToDTO
        );
    }

    @Test
    void getCategory_CategoryNotFound_ThrowsException() {
        when(categoryRepository.findByCategoryId(eq(CATEGORY_UUID))).thenReturn(Optional.empty());

        assertThatThrownBy(() -> categoryService.getCategory(CATEGORY_ID))
            .isInstanceOf(IntentionerException.class)
            .hasFieldOrPropertyWithValue("code", HttpStatus.NOT_FOUND.value());
    }

    @Test
    void getCategory_CategoryExists_MapsToDTO() {
        when(categoryRepository.findByCategoryId(eq(CATEGORY_UUID))).thenReturn(Optional.of(CATEGORY));
        when(categoryEntityToDTO.apply(eq(CATEGORY))).thenReturn(CATEGORY_DTO);

        categoryService.getCategory(CATEGORY_ID);

        verify(categoryEntityToDTO).apply(eq(CATEGORY));
    }

    @Test
    void updateCategory_CategoryExists_MapsUpdatedEntityToDTO() {
        when(categoryRepository.findByCategoryId(any(UUID.class))).thenReturn(Optional.of(CATEGORY));
        when(categoryRepository.saveAndFlush(eq(UPDATED_CATEGORY))).thenReturn(UPDATED_CATEGORY);

        categoryService.updateCategory(CATEGORY_ID, UPDATE_CATEGORY_REQUEST);

        verify(categoryEntityToDTO).apply(eq(UPDATED_CATEGORY));
    }

    @Test
    void updateCategory_CategoryWithProvidedIdDoesNotExist_ThrowsException() {
        when(categoryRepository.findByCategoryId(any(UUID.class))).thenReturn(Optional.empty());

        assertThatThrownBy(() -> categoryService.updateCategory(CATEGORY_ID, UPDATE_CATEGORY_REQUEST))
            .isInstanceOfAny(IntentionerException.class)
            .hasFieldOrPropertyWithValue("code", HttpStatus.NOT_FOUND.value());
    }

    @Test
    void deleteCategory_CategoryWithProvidedIdDoesNotExist_ThrowsException() {
        when(categoryRepository.findByCategoryId(any(UUID.class))).thenReturn(Optional.empty());

        assertThatThrownBy(() -> categoryService.deleteCategory(CATEGORY_ID))
            .isInstanceOfAny(IntentionerException.class)
            .hasFieldOrPropertyWithValue("code", HttpStatus.NOT_FOUND.value());
    }

    @Test
    void deleteCategory_CategoryLinkedToIntentions_ThrowsException() {
        when(categoryRepository.findByCategoryId(any(UUID.class))).thenReturn(Optional.of(CATEGORY));
        when(intentionRepository.findAllByCategory(eq(CATEGORY))).thenReturn(List.of(INTENTION));

        assertThatThrownBy(() -> categoryService.deleteCategory(CATEGORY_ID))
            .isInstanceOfAny(IntentionerException.class)
            .hasFieldOrPropertyWithValue("code", HttpStatus.CONFLICT.value());
    }

    @Test
    void deleteCategory_CategoryWithoutLinksToIntentions_DeletedViaRepository() {
        when(categoryRepository.findByCategoryId(any(UUID.class))).thenReturn(Optional.of(CATEGORY));
        when(intentionRepository.findAllByCategory(eq(CATEGORY))).thenReturn(List.of());

        categoryService.deleteCategory(CATEGORY_ID);

        verify(categoryRepository).deleteById(eq(CATEGORY.getId()));
    }
}