package sk.taron.intentioner.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import java.util.Objects;
import java.util.UUID;

/**
 * Intention entity.
 */
@Entity(name = "intention")
public class Intention {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String text;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @Column(unique = true, nullable = false)
    private UUID intentionId;

    /**
     * The empty constructor required for hibernate.
     */
    public Intention() {
    }

    /**
     * The constructor used for creating new entity.
     *
     * @param text     the intention's text
     * @param category the category to which is intention related
     */
    public Intention(String text, Category category) {
        this.text = text;
        this.category = category;
        this.intentionId = UUID.randomUUID();
    }

    /**
     * The constructor used for updating existing intention.
     *
     * @param id          the id
     * @param text        the text
     * @param category    the related category
     * @param intentionId the intention id
     */
    public Intention(Long id, String text, Category category, UUID intentionId) {
        this.id = id;
        this.text = text;
        this.category = category;
        this.intentionId = intentionId;
    }

    public Long getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public Category getCategory() {
        return category;
    }

    public UUID getIntentionId() {
        return intentionId;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        Intention intention = (Intention) obj;
        return Objects.equals(id, intention.id)
            && Objects.equals(text, intention.text)
            && Objects.equals(category, intention.category)
            && Objects.equals(intentionId, intention.intentionId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, text, category, intentionId);
    }
}
