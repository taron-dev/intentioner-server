package sk.taron.intentioner;

import java.util.List;

import sk.taron.intentioner.model.IntentionDTO;
import sk.taron.intentioner.model.UpdateIntentionRequest;

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
     * Get intetnion by id.
     * 
     * @param intentionId the id of the intenion
     * @return found intention
     */
    IntentionDTO getIntention(String intentionId);

    /**
     * Save intention.
     * 
     * @param intention the intention to be saved
     * @return saved intenion
     */
    IntentionDTO saveIntention(IntentionDTO intention);

    /**
     * Update existing intenion
     * @param intentionId the id of the existing intention
     * @param updateIntentionRequest contains data to be updated
     * @return the updated intention
     */
    IntentionDTO updateIntention(String intentionId, UpdateIntentionRequest updateIntentionRequest);

    /**
     * Delete intention.
     * 
     * @param intentionId the id of the intention to be deleted
     */
    void deleteIntention(String intentionId);
}