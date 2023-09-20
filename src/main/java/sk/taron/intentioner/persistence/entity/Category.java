package sk.taron.intentioner.persistence.entity;

import jakarta.persistence.*;

import java.util.UUID;

/**
 * Intention category entity.
 */
@Entity(name = "category")
public class Category {

    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String label;

    @Column(unique = true, nullable = false)
    private UUID categoryId;

    public Category() {
    }

    public Category(String name, String label) {
        this.name = name;
        this.label = label;
    }

    public Category(Long id, String name, String label, UUID categoryId) {
        this.id = id;
        this.name = name;
        this.label = label;
        this.categoryId = categoryId;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getLabel() {
        return label;
    }

    public UUID getCategoryId() {
        return categoryId;
    }

    @PrePersist
    public void generateUUID() {
        if (categoryId == null) {
            categoryId = UUID.randomUUID();
        }
    }
}
