package sk.taron.intentioner.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * Intention category entity.
 */
@Entity(name = "category")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        Category category = (Category) obj;
        return Objects.equals(id, category.id)
            && Objects.equals(name, category.name)
            && Objects.equals(label, category.label)
            && Objects.equals(categoryId, category.categoryId)
            && Objects.equals(intentions, category.intentions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, label, categoryId, intentions);
    }
}
