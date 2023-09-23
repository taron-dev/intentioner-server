package sk.taron.intentioner;

import sk.taron.intentioner.model.CategoryDTO;
import sk.taron.intentioner.model.UpdateCategoryRequest;
import sk.taron.intentioner.persistence.entity.Category;

import java.util.UUID;

public class CategoryDataProvider {

    public static final String CATEGORY_ID = "1c6eb650-28f1-41b6-8171-8b42d02ab9ab";
    public static final UUID CATEGORY_UUID = UUID.fromString(CATEGORY_ID);
    public static final String LABEL = "label";
    public static final String NAME = "name";
    public static final Long ID = 1L;

    public static final UpdateCategoryRequest UPDATE_CATEGORY_REQUEST = new UpdateCategoryRequest(
        LABEL,
        NAME
    );

    public static final CategoryDTO CATEGORY_DTO = new CategoryDTO(
        LABEL,
        NAME,
        CATEGORY_ID
    );

    public static final Category CATEGORY = new Category(
        ID,
        NAME,
        LABEL,
        CATEGORY_UUID
    );
}
