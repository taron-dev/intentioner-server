package sk.taron.intentioner;

import sk.taron.intentioner.model.UpdateCategoryRequest;

public class CategoryDataProvider {

    public static final String CATEGORY_ID = "1c6eb650-28f1-41b6-8171-8b42d02ab9ab";
    public static final String LABEL = "label";
    public static final String NAME = "name";

    public static final UpdateCategoryRequest UPDATE_CATEGORY_REQUEST = new UpdateCategoryRequest(
        LABEL,
        NAME
    );
}
