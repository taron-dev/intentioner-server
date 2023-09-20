package sk.taron.intentioner.mvc;

import java.util.List;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import sk.taron.intentioner.IntentionService;
import sk.taron.intentioner.model.IntentionDTO;
import sk.taron.intentioner.model.UpdateIntentionRequest;

/**
 * The rest fontroller for intentions.
 */
@RestController
@RequestMapping("/api/v1/intentions")
@Validated
public class IntentionsController {

    private final IntentionService intentionService;

    /**
     * The constructor.
     * 
     * @param intentionService intention service
     */
    public IntentionsController(IntentionService intentionService) {
        this.intentionService = intentionService;
    }

    /**
     * Get all intentions.
     * 
     * @return list of intentions
     */
    @GetMapping
    public List<IntentionDTO> getAllIntentions(){
        return intentionService.getAllIntentions();
    }

    /**
     * Save intention.
     * 
     * @param intention the intention to save
     * @return saved intention
     */
    @PostMapping
    public IntentionDTO saveIntention(@Valid @RequestBody IntentionDTO intention){
        return intentionService.saveIntention(intention);
    }

    // TODO - UUID pattern constraint
    /**
     * Delete intention.
     * 
     * @param intentionId the id of the intention to be deleted
     */
    @DeleteMapping("/{intentionId}")
    public void deleteIntention(@PathVariable String intentionId){
        intentionService.deleteIntention(intentionId);
    }

    /**
     * Get intention.
     * 
     * @param intentionId the id of the intention to be retrieved
     * @return the intention
     */
    @GetMapping("/{intentionId}")
    public IntentionDTO getIntention(@PathVariable String intentionId){
        return intentionService.getIntention(intentionId);
    }

    /**
     * Update intention.
     * 
     * @param intentionId the id of the intention to be updated
     * @param updateIntentionRequest contains data to be updated
     * @return the updated intetnion
     */
    @PutMapping("/{intentionId}")
    public IntentionDTO updateIntention(
        @PathVariable String intentionId,
        @Valid @RequestBody UpdateIntentionRequest updateIntentionRequest
    ){
        return intentionService.updateIntention(intentionId, updateIntentionRequest);
    }
}
