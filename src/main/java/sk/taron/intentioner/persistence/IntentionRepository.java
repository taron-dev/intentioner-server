package sk.taron.intentioner.persistence;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import sk.taron.intentioner.persistence.entity.Category;
import sk.taron.intentioner.persistence.entity.Intention;

public interface IntentionRepository extends JpaRepository<Intention, Long> {
    
    Optional<Intention> findByIntentionId(UUID intentionId);

    List<Intention> findAllByCategory(Category category);
}
