package sk.taron.intentioner.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

import java.util.List;
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

    @OneToMany(mappedBy = "category")
    private List<Intention> intentions;

    /**
     * The empty constructor required by hibernate.
     */
    public Category() {
    }

    /**
     * The constructor used for creating new category.
     *
     * @param name  the name
     * @param label the label
     */
    public Category(String name, String label) {
        this.name = name;
        this.label = label;
        this.categoryId = UUID.randomUUID();
    }

    /**
     * The constructor used for updating existing category.
     *
     * @param id         the id
     * @param name       the name
     * @param label      the label
     * @param categoryId the category id
     */
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
}
