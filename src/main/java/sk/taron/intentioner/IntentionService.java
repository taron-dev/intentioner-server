package sk.taron.intentioner;

import java.util.List;

import sk.taron.intentioner.model.IntentionDTO;
import sk.taron.intentioner.model.IntentionModificationRequest;

/**
 * Provides operations over intention.
 */
public interface IntentionService {

    /**
     * Get all intentions.
     * 
     * @return list of intentions
     */
    List<IntentionDTO> getAllIntentions();

    /**
     * Get intention by id.
     * 
     * @param intentionId the id of the intention
     * @return found intention
     */
    IntentionDTO getIntention(String intentionId);

    /**
     * Save intention.
     * 
     * @param intentionModificationRequest contains data of the intention
     * @return saved intention
     */
    IntentionDTO saveIntention(IntentionModificationRequest intentionModificationRequest);

    /**
     * Update existing intention
     *
     * @param intentionId the id of the existing intention
     * @param intentionModificationRequest contains data to be updated
     * @return the updated intention
     */
    IntentionDTO updateIntention(String intentionId, IntentionModificationRequest intentionModificationRequest);

    /**
     * Delete intention.
     * 
     * @param intentionId the id of the intention to be deleted
     */
    void deleteIntention(String intentionId);
}