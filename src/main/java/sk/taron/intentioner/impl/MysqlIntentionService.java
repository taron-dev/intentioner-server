package sk.taron.intentioner.impl;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import jakarta.inject.Named;
import sk.taron.intentioner.IntentionService;
import sk.taron.intentioner.mapper.CategoryDTOToEntity;
import sk.taron.intentioner.mapper.IntentionDTOToEntity;
import sk.taron.intentioner.mapper.IntentionEntityToDTO;
import sk.taron.intentioner.model.IntentionDTO;
import sk.taron.intentioner.model.UpdateIntentionRequest;
import sk.taron.intentioner.persistence.IntentionRepository;
import sk.taron.intentioner.persistence.entity.Intention;

/**
 * The intention service relies on data from mysql.
 */
@Named
public class MysqlIntentionService implements IntentionService {

    private final IntentionRepository intentionRepository;
    private final IntentionDTOToEntity intentionDTOToEntity;
    private final IntentionEntityToDTO intentionEntityToDTO;
    private final CategoryDTOToEntity categoryDTOToEntity;

    /**
     * The constructor.
     * 
     * @param intentionRepository the intetnion repository
     * @param intentionDTOToEntity maps DTO to entity
     * @param intentionEntityToDTO maps entity to DTO
     * @param categoryDTOToEntity maps category DTO to entity
     */
    public MysqlIntentionService(
        IntentionRepository intentionRepository,
        IntentionDTOToEntity intentionDTOToEntity,
        IntentionEntityToDTO intentionEntityToDTO,
        CategoryDTOToEntity categoryDTOToEntity
    ) {
        this.intentionRepository = intentionRepository;
        this.intentionDTOToEntity = intentionDTOToEntity;
        this.intentionEntityToDTO = intentionEntityToDTO;
        this.categoryDTOToEntity = categoryDTOToEntity;
    }

    @Override
    public List<IntentionDTO> getAllIntentions() {
        return intentionRepository.findAll()
            .stream()
            .map(intentionEntityToDTO)
            .collect(Collectors.toList());
    }

    @Override
    public IntentionDTO getIntention(String intentionId) {
        UUID uuid = UUID.fromString(intentionId);

        // TODO - throw proper exception
        return intentionRepository.findByIntentionId(uuid)
            .map(intentionEntityToDTO)
            .orElseThrow();
    }

    @Override
    public IntentionDTO saveIntention(IntentionDTO intentionDTO) {
        Intention intenion = intentionDTOToEntity.apply(intentionDTO);
        intenion = intentionRepository.saveAndFlush(intenion);
        return intentionEntityToDTO.apply(intenion);
    }

    @Override
    public IntentionDTO updateIntention(String intentionId, UpdateIntentionRequest updateIntentionRequest) {
        UUID uuid = UUID.fromString(intentionId);

        Intention intention = intentionRepository.findByIntentionId(uuid).orElseThrow();
        intention = new Intention(
            intention.getId(),
            updateIntentionRequest.text(),
            categoryDTOToEntity.apply(updateIntentionRequest.category()),
            uuid
        );
        
        intention = intentionRepository.saveAndFlush(intention);
        return intentionEntityToDTO.apply(intention);
    }

    @Override
    public void deleteIntention(String intentionId) {
        UUID uuid = UUID.fromString(intentionId);

        Intention intention = intentionRepository.findByIntentionId(uuid).orElseThrow();
        intentionRepository.deleteById(intention.getId());
    }
    
}
