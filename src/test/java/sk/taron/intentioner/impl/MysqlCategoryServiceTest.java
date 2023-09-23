package sk.taron.intentioner.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import sk.taron.intentioner.CategoryDataProvider;
import sk.taron.intentioner.CategoryService;
import sk.taron.intentioner.IntentionerException;
import sk.taron.intentioner.mapper.CategoryDTOToEntity;
import sk.taron.intentioner.mapper.CategoryEntityToDTO;
import sk.taron.intentioner.model.UpdateCategoryRequest;
import sk.taron.intentioner.persistence.CategoryRepository;
import sk.taron.intentioner.persistence.IntentionRepository;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static sk.taron.intentioner.CategoryDataProvider.CATEGORY_ID;
import static sk.taron.intentioner.CategoryDataProvider.UPDATE_CATEGORY_REQUEST;

/**
 * Tests for {@link CategoryService} implemented by {@link MysqlCategoryService}.
 */
@ExtendWith(MockitoExtension.class)
class MysqlCategoryServiceTest {

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
    void setUp(){
        this.categoryService = new MysqlCategoryService(
            categoryRepository,
            intentionRepository,
            categoryDTOtoEntity,
            categoryEntityToDTO
        );
    }

    @Test
    void updateCategory_CategoryWithProvidedIdDoesNotExist_ThrowsException() {
        when(categoryRepository.findByCategoryId(any(UUID.class))).thenReturn(Optional.empty());

        assertThatThrownBy(() -> categoryService.updateCategory(CATEGORY_ID,UPDATE_CATEGORY_REQUEST))
            .isInstanceOfAny(IntentionerException.class)
            .hasFieldOrPropertyWithValue("code", 404);
    }

    @Test
    void deleteCategory_CategoryWithProvidedIdDoesNotExist_ThrowsException() {
        when(categoryRepository.findByCategoryId(any(UUID.class))).thenReturn(Optional.empty());

        assertThatThrownBy(() -> categoryService.deleteCategory(CATEGORY_ID))
            .isInstanceOfAny(IntentionerException.class)
            .hasFieldOrPropertyWithValue("code", 404);
    }
}