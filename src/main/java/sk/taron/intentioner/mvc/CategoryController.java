package sk.taron.intentioner.mvc;

import jakarta.validation.Valid;
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
import sk.taron.intentioner.CategoryService;
import sk.taron.intentioner.model.CategoryDTO;
import sk.taron.intentioner.model.UpdateCategoryRequest;

import java.util.List;

@RestController
@RequestMapping("/api/v1/categories")
@Validated
public class CategoryController {

    private static final String UUID_PATTERN = "^[{]?[0-9a-fA-F]{8}-([0-9a-fA-F]{4}-){3}[0-9a-fA-F]{12}[}]?$";

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping
    public CategoryDTO saveCategory(@Valid @RequestBody CategoryDTO category) {
        return categoryService.saveCategory(category);
    }

    @GetMapping
    public List<CategoryDTO> getCategories() {
        return categoryService.getAllCategories();
    }

    @GetMapping("/{categoryId}")
    public CategoryDTO getCategory(@PathVariable @Pattern(regexp = UUID_PATTERN) String categoryId) {
        return categoryService.getCategory(categoryId);
    }

    @PutMapping("/{categoryId}")
    public CategoryDTO updateCategory(
        @PathVariable @Pattern(regexp = UUID_PATTERN) String categoryId,
        @Valid @RequestBody UpdateCategoryRequest updateCategoryRequest
    ) {
        return categoryService.updateCategory(categoryId, updateCategoryRequest);
    }

    @DeleteMapping("/{categoryId}")
    public void deleteCategory(@PathVariable @Pattern(regexp = UUID_PATTERN) String categoryId) {
        categoryService.deleteCategory(categoryId);
    }
}
