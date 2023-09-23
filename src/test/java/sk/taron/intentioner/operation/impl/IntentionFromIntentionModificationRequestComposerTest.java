package sk.taron.intentioner.operation.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import sk.taron.intentioner.model.IntentionModificationRequest;
import sk.taron.intentioner.operation.IntentionFromIntentionModificationRequest;
import sk.taron.intentioner.persistence.CategoryRepository;
import sk.taron.intentioner.persistence.entity.Category;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

/**
 * Tests for {@link IntentionFromIntentionModificationRequest} implemented by {@link IntentionFromIntentionModificationRequestComposer}.
 */
@ExtendWith(MockitoExtension.class)
class IntentionFromIntentionModificationRequestComposerTest {

    private static final String CATEGORY_ID = "categoryId";
    private static final String TEXT = "text";
    private static final UUID CATEGORY_UUID = UUID.fromString(CATEGORY_ID);
    private static final IntentionModificationRequest REQUEST = new IntentionModificationRequest(
        TEXT,
        CATEGORY_ID
    );
    private static final Category CATEGORY = new Category();

    @Mock
    private CategoryRepository categoryRepository;

    private IntentionFromIntentionModificationRequest sut;


    @BeforeEach
    void setUp() {
        sut = new IntentionFromIntentionModificationRequestComposer(categoryRepository);
    }

    @Test
    void apply_categoryWithIdDoesNotExist_ReturnsEmptyOptional() {
        when(categoryRepository.findByCategoryId(eq(CATEGORY_UUID))).thenReturn(Optional.empty());

        assertThat(sut.apply(REQUEST).isEmpty()).isTrue();
    }

    @Test
    void apply_categoryWithIdPresent_ReturnsIntention() {
        when(categoryRepository.findByCategoryId(eq(CATEGORY_UUID))).thenReturn(Optional.empty());

        assertThat(sut.apply(REQUEST).isEmpty()).isTrue();
    }
}