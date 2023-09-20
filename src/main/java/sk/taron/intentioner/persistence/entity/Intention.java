package sk.taron.intentioner.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;

import java.util.UUID;

@Entity(name = "intention")
public class Intention {

    @Id
    @GeneratedValue
    private Long id;

    private String text;

    @OneToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @Column(unique = true, nullable = false)
    private UUID intentionId;

    public Intention() {
    }

    public Intention(String text, Category category) {
        this.text = text;
        this.category = category;
    }

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

    // TODO - generate UUID
}
