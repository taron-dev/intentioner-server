package sk.taron.intentioner.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import sk.taron.intentioner.persistence.entity.Category;

import java.util.Optional;
import java.util.UUID;

/**
 * Repository which provides category data from DB.
 */
public interface CategoryRepository extends JpaRepository<Category, Long> {

    Optional<Category> findByCategoryId(UUID categoryId);
}
