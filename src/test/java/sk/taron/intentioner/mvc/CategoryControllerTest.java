package sk.taron.intentioner.mvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import sk.taron.intentioner.CategoryService;
import sk.taron.intentioner.IntentionerException;
import sk.taron.intentioner.model.CategoryDTO;
import sk.taron.intentioner.model.UpdateCategoryRequest;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static sk.taron.intentioner.CategoryDataProvider.CATEGORY_ID;
import static sk.taron.intentioner.CategoryDataProvider.LABEL;
import static sk.taron.intentioner.CategoryDataProvider.NAME;
import static sk.taron.intentioner.CategoryDataProvider.UPDATE_CATEGORY_REQUEST;

@WebMvcTest(CategoryController.class)
class CategoryControllerTest {

    private static final String INVALID_CATEGORY_ID = "invalidCategoryId";
    private static final String NOT_FOUND_MESSAGE = "Category with id " + CATEGORY_ID + " was not found";

    private static final UpdateCategoryRequest UPDATE_CATEGORY_REQUEST_INVALID = new UpdateCategoryRequest(
        LABEL,
        null
    );

    private static final CategoryDTO CATEGORY_DTO_NULL_CATEGORY_ID = new CategoryDTO(
        LABEL,
        NAME,
        null
    );

    private static final CategoryDTO CATEGORY_DTO_INVALID = new CategoryDTO(
        null,
        null,
        null
    );

    @MockBean
    private CategoryService categoryService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void updateCategory_InvalidRequestBody_ThrowsException() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/api/v1/categories/{categoryId}", CATEGORY_ID)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(UPDATE_CATEGORY_REQUEST_INVALID));

        mockMvc.perform(requestBuilder)
            .andExpect(status().isBadRequest());
    }

    @Test
    void saveCategory_InvalidRequestBody_ThrowsException() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/v1/categories")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(CATEGORY_DTO_INVALID));

        mockMvc.perform(requestBuilder)
            .andExpect(status().isBadRequest());
    }

    @Test
    void saveCategory_CategoryIdIsNotPresent_SavesCategory() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/v1/categories")
            .content(objectMapper.writeValueAsString(CATEGORY_DTO_NULL_CATEGORY_ID))
            .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder)
            .andExpect(status().isOk());
    }

    @Test
    void deleteCategory_CategoryIdIsNotUUID_ThrowsException() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/api/v1/categories/{categoryId}", INVALID_CATEGORY_ID)
            .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder)
            .andExpect(status().isBadRequest());
    }


    @Test
    void getCategory_CategoryWithIdDoesNotExist_ThrowsException() throws Exception {
        doThrow(new IntentionerException(404, NOT_FOUND_MESSAGE))
            .when(categoryService).getCategory(eq(CATEGORY_ID));

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/v1/categories/{categoryId}", CATEGORY_ID)
            .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder)
            .andExpect(status().isNotFound());
    }

    @Test
    void updateCategory_CategoryWithIdDoesNotExist_ThrowsException() throws Exception {
        doThrow(new IntentionerException(404, NOT_FOUND_MESSAGE))
            .when(categoryService).updateCategory(eq(CATEGORY_ID), eq(UPDATE_CATEGORY_REQUEST));

        RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/api/v1/categories/{categoryId}", CATEGORY_ID)
            .content(objectMapper.writeValueAsString(UPDATE_CATEGORY_REQUEST))
            .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder)
            .andExpect(status().isNotFound());
    }

    @Test
    void deleteCategory_CategoryWithIdDoesNotExist_ThrowsException() throws Exception {
        doThrow(new IntentionerException(404, NOT_FOUND_MESSAGE))
            .when(categoryService).deleteCategory(eq(CATEGORY_ID));

        RequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/api/v1/categories/{categoryId}", CATEGORY_ID)
            .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder)
            .andExpect(status().isNotFound());
    }
}