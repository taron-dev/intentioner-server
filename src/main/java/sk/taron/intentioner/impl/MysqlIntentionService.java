package sk.taron.intentioner.impl;

import jakarta.inject.Named;
import org.springframework.http.HttpStatus;
import sk.taron.intentioner.IntentionService;
import sk.taron.intentioner.IntentionerException;
import sk.taron.intentioner.mapper.IntentionEntityToDTO;
import sk.taron.intentioner.model.IntentionDTO;
import sk.taron.intentioner.model.IntentionModificationRequest;
import sk.taron.intentioner.operation.IntentionFromIntentionModificationRequest;
import sk.taron.intentioner.persistence.CategoryRepository;
import sk.taron.intentioner.persistence.IntentionRepository;
import sk.taron.intentioner.persistence.entity.Category;
import sk.taron.intentioner.persistence.entity.Intention;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * The intention service.
 */
// TODO - functional tests
@Named
public class MysqlIntentionService implements IntentionService {

    private final IntentionRepository intentionRepository;
    private final CategoryRepository categoryRepository;
    private final IntentionFromIntentionModificationRequest intentionFromIntentionModificationRequest;
    private final IntentionEntityToDTO intentionEntityToDTO;

    /**
     * The constructor.
     *
     * @param intentionRepository                     the intention repository
     * @param categoryRepository                      the category repository
     * @param intentionFromIntentionModificationRequest compose intention entity from save intention request
     * @param intentionEntityToDTO                    maps entity to DTO
     */
    public MysqlIntentionService(
        IntentionRepository intentionRepository,
        CategoryRepository categoryRepository,
        IntentionFromIntentionModificationRequest intentionFromIntentionModificationRequest,
        IntentionEntityToDTO intentionEntityToDTO
    ) {
        this.intentionRepository = intentionRepository;
        this.categoryRepository = categoryRepository;
        this.intentionFromIntentionModificationRequest = intentionFromIntentionModificationRequest;
        this.intentionEntityToDTO = intentionEntityToDTO;
    }

    /**
     * Get all intentions.
     *
     * @return list of intentions
     */
    @Override
    public List<IntentionDTO> getAllIntentions() {
        return intentionRepository.findAll()
            .stream()
            .map(intentionEntityToDTO)
            .collect(Collectors.toList());
    }

    /**
     * Get intention by id.
     *
     * @param intentionId the id of the intention
     * @return the intention or throws intentioner exception
     */
    @Override
    public IntentionDTO getIntention(String intentionId) {
        UUID uuid = UUID.fromString(intentionId);

        return intentionRepository.findByIntentionId(uuid)
            .map(intentionEntityToDTO)
            .orElseThrow(() -> new IntentionerException(
                HttpStatus.NOT_FOUND.value(),
                "No intention found for id: " + intentionId
            ));
    }

    /**
     * Save intention.
     *
     * @param intentionModificationRequest contains data of the intention
     * @return he saved intention
     */
    @Override
    public IntentionDTO saveIntention(IntentionModificationRequest intentionModificationRequest) {
        Intention intention = intentionFromIntentionModificationRequest.apply(intentionModificationRequest)
            .orElseThrow(() -> new IntentionerException(
                HttpStatus.NOT_FOUND.value(),
                "No category exists for provided id: " + intentionModificationRequest.categoryId()
            ));

        intention = intentionRepository.saveAndFlush(intention);
        return intentionEntityToDTO.apply(intention);
    }

    /**
     * Update existing intention
     *
     * @param intentionId                  the id of the existing intention
     * @param intentionModificationRequest contains data to be updated
     * @return the updated intention
     */
    @Override
    public IntentionDTO updateIntention(String intentionId, IntentionModificationRequest intentionModificationRequest) {
        UUID intentionUUID = UUID.fromString(intentionId);

        Intention intention = intentionRepository.findByIntentionId(intentionUUID)
            .orElseThrow(() -> new IntentionerException(
                HttpStatus.NOT_FOUND.value(),
                "No intention found for id: " + intentionId
            ));

        UUID categoryUUID = UUID.fromString(intentionModificationRequest.categoryId());

        Category category = categoryRepository.findByCategoryId(categoryUUID)
            .orElseThrow(() -> new IntentionerException(
                HttpStatus.NOT_FOUND.value(),
                "No category found for id: " + intentionModificationRequest.categoryId()
            ));

        intention = new Intention(
            intention.getId(),
            intentionModificationRequest.text(),
            category,
            intentionUUID
        );

        intention = intentionRepository.saveAndFlush(intention);
        return intentionEntityToDTO.apply(intention);
    }

    /**
     * Delete intention.
     *
     * @param intentionId the id of the intention to be deleted
     */
    @Override
    public void deleteIntention(String intentionId) {
        UUID uuid = UUID.fromString(intentionId);

        Intention intention = intentionRepository.findByIntentionId(uuid)
            .orElseThrow(() -> new IntentionerException(
                HttpStatus.NOT_FOUND.value(),
                "No intention found for id: " + intentionId
            ));

        intentionRepository.deleteById(intention.getId());
    }
}
