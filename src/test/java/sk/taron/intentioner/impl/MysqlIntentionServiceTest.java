package sk.taron.intentioner.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import sk.taron.intentioner.IntentionService;
import sk.taron.intentioner.IntentionerException;
import sk.taron.intentioner.mapper.IntentionEntityToDTO;
import sk.taron.intentioner.model.IntentionDTO;
import sk.taron.intentioner.model.IntentionModificationRequest;
import sk.taron.intentioner.operation.IntentionFromIntentionModificationRequest;
import sk.taron.intentioner.persistence.CategoryRepository;
import sk.taron.intentioner.persistence.IntentionRepository;
import sk.taron.intentioner.persistence.entity.Intention;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static sk.taron.intentioner.CategoryDataProvider.CATEGORY;
import static sk.taron.intentioner.CategoryDataProvider.CATEGORY_DTO;
import static sk.taron.intentioner.CategoryDataProvider.CATEGORY_ID;
import static sk.taron.intentioner.CategoryDataProvider.CATEGORY_UUID;

/**
 * Tests for {@link IntentionService} implemented by {@link MysqlIntentionService}.
 */
@ExtendWith(MockitoExtension.class)
class MysqlIntentionServiceTest {

    private static final Long ID = 1L;
    private static final String TEXT = "text";
    private static final String INTENTION_ID = "1c6eb650-28f1-41b6-8171-8b42d02ab9ab";
    private static final UUID INTENTION_UUID = UUID.fromString(INTENTION_ID);

    private static final Intention INTENTION = new Intention(
        ID,
        TEXT,
        CATEGORY,
        INTENTION_UUID
    );

    private static final Intention UPDATED_INTENTION = new Intention(
        INTENTION.getId(),
        TEXT,
        CATEGORY,
        INTENTION_UUID
    );

    private static final IntentionDTO INTENTION_DTO = new IntentionDTO(
        TEXT,
        INTENTION_UUID,
        CATEGORY_DTO
    );

    private static final IntentionModificationRequest INTENTION_MODIFICATION_REQUEST = new IntentionModificationRequest(
        TEXT,
        CATEGORY_ID
    );

    @Mock
    private IntentionRepository intentionRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private IntentionFromIntentionModificationRequest intentionFromIntentionModificationRequest;

    @Mock
    private IntentionEntityToDTO intentionEntityToDTO;

    private IntentionService intentionService;

    @BeforeEach
    void setUp() {
        this.intentionService = new MysqlIntentionService(
            intentionRepository,
            categoryRepository,
            intentionFromIntentionModificationRequest,
            intentionEntityToDTO
        );
    }

    @Test
    void getIntention_IntentionNotFound_ThrowsException() {
        when(intentionRepository.findByIntentionId(eq(INTENTION_UUID)))
            .thenReturn(Optional.empty());

        assertThatThrownBy(() -> intentionService.getIntention(INTENTION_ID))
            .isInstanceOfAny(IntentionerException.class)
            .hasFieldOrPropertyWithValue("code", 404);
    }

    @Test
    void getIntention_IntentionExists_MapsToDTO() {
        when(intentionRepository.findByIntentionId(eq(INTENTION_UUID)))
            .thenReturn(Optional.of(INTENTION));
        when(intentionEntityToDTO.apply(eq(INTENTION))).thenReturn(INTENTION_DTO);

        intentionService.getIntention(INTENTION_ID);

        verify(intentionEntityToDTO).apply(eq(INTENTION));
    }

    @Test
    void saveIntention_CanNotComposeIntentionFromRequest_ThrowsException() {
        when(intentionFromIntentionModificationRequest.apply(eq(INTENTION_MODIFICATION_REQUEST)))
            .thenReturn(Optional.empty());

        assertThatThrownBy(() -> intentionService.saveIntention(INTENTION_MODIFICATION_REQUEST))
            .isInstanceOfAny(IntentionerException.class)
            .hasFieldOrPropertyWithValue("code", 404);
    }

    @Test
    void saveIntention_IntentionComposedFromRequest_ReturnsDTO() {
        when(intentionFromIntentionModificationRequest.apply(eq(INTENTION_MODIFICATION_REQUEST)))
            .thenReturn(Optional.of(INTENTION));
        when(intentionRepository.saveAndFlush(eq(INTENTION))).thenReturn(INTENTION);
        when(intentionEntityToDTO.apply(eq(INTENTION))).thenReturn(INTENTION_DTO);

        assertThat(intentionService.saveIntention(INTENTION_MODIFICATION_REQUEST)).isEqualTo(INTENTION_DTO);
    }

    @Test
    void updateIntention_IntentionNotFound_ThrowsException() {
        when(intentionRepository.findByIntentionId(eq(INTENTION_UUID)))
            .thenReturn(Optional.empty());

        assertThatThrownBy(() -> intentionService.updateIntention(
            INTENTION_ID,
            INTENTION_MODIFICATION_REQUEST
        ))
            .isInstanceOfAny(IntentionerException.class)
            .hasFieldOrPropertyWithValue("code", 404);
    }

    @Test
    void updateIntention_CategoryNotFound_ThrowsException() {
        when(intentionRepository.findByIntentionId(eq(INTENTION_UUID)))
            .thenReturn(Optional.of(INTENTION));

        when(categoryRepository.findByCategoryId(eq(CATEGORY_UUID)))
            .thenReturn(Optional.empty());

        assertThatThrownBy(() -> intentionService.updateIntention(
            INTENTION_ID,
            INTENTION_MODIFICATION_REQUEST
        ))
            .isInstanceOfAny(IntentionerException.class)
            .hasFieldOrPropertyWithValue("code", 404);
    }

    @Test
    void updateIntention_UpdatedIntentionCreated_MapsToDTO() {
        when(intentionRepository.findByIntentionId(eq(INTENTION_UUID)))
            .thenReturn(Optional.of(INTENTION));

        when(categoryRepository.findByCategoryId(eq(CATEGORY_UUID)))
            .thenReturn(Optional.of(CATEGORY));

        when(intentionRepository.saveAndFlush(eq(UPDATED_INTENTION))).thenReturn(UPDATED_INTENTION);

        intentionService.updateIntention(
            INTENTION_ID,
            INTENTION_MODIFICATION_REQUEST
        );

        verify(intentionEntityToDTO).apply(eq(UPDATED_INTENTION));
    }

    @Test
    void deleteIntention_IntentionNotFound_ThrowsException() {
        when(intentionRepository.findByIntentionId(eq(INTENTION_UUID)))
            .thenReturn(Optional.empty());

        assertThatThrownBy(() -> intentionService.deleteIntention(INTENTION_ID))
            .isInstanceOfAny(IntentionerException.class)
            .hasFieldOrPropertyWithValue("code", 404);
    }

    @Test
    void deleteIntention_IntentionExists_RemovesIntention() {
        when(intentionRepository.findByIntentionId(eq(INTENTION_UUID)))
            .thenReturn(Optional.of(INTENTION));

        intentionService.deleteIntention(INTENTION_ID);
        verify(intentionRepository).deleteById(eq(INTENTION.getId()));
    }
}