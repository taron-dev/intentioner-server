package sk.taron.intentioner.persistence;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import sk.taron.intentioner.persistence.entity.Intention;

public interface IntentionRepository extends JpaRepository<Intention, Long> {
    
    Optional<Intention> findByIntentionId(UUID intentionId);
}
