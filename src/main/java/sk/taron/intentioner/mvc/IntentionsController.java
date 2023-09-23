package sk.taron.intentioner.mvc;

import java.util.List;

import jakarta.validation.constraints.Pattern;
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
import sk.taron.intentioner.model.IntentionModificationRequest;

/**
 * The rest controller for intentions.
 */
@RestController
@RequestMapping("/api/v1/intentions")
@Validated
public class IntentionsController {

    private static final String UUID_PATTERN = "^[{]?[0-9a-fA-F]{8}-([0-9a-fA-F]{4}-){3}[0-9a-fA-F]{12}[}]?$";

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
     * @param intentionModificationRequest the intention to save
     * @return saved intention
     */
    @PostMapping
    public IntentionDTO saveIntention(@Valid @RequestBody IntentionModificationRequest intentionModificationRequest){
        return intentionService.saveIntention(intentionModificationRequest);
    }

    /**
     * Delete intention.
     * 
     * @param intentionId the id of the intention to be deleted
     */
    @DeleteMapping("/{intentionId}")
    public void deleteIntention(@PathVariable @Pattern(regexp = UUID_PATTERN) String intentionId){
        intentionService.deleteIntention(intentionId);
    }

    /**
     * Get intention.
     * 
     * @param intentionId the id of the intention to be retrieved
     * @return the intention
     */
    @GetMapping("/{intentionId}")
    public IntentionDTO getIntention(@PathVariable @Pattern(regexp = UUID_PATTERN) String intentionId){
        return intentionService.getIntention(intentionId);
    }

    /**
     * Update intention.
     * 
     * @param intentionId the id of the intention to be updated
     * @param intentionModificationRequest contains data to be updated
     * @return the updated intention
     */
    @PutMapping("/{intentionId}")
    public IntentionDTO updateIntention(
        @PathVariable @Pattern(regexp = UUID_PATTERN) String intentionId,
        @Valid @RequestBody IntentionModificationRequest intentionModificationRequest
    ){
        return intentionService.updateIntention(intentionId, intentionModificationRequest);
    }
}
