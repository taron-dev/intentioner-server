package sk.taron.intentioner;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import sk.taron.intentioner.model.CategoryDTO;
import sk.taron.intentioner.model.UpdateCategoryRequest;

import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class CategoryTest {

    private static final String CATEGORIES_API_PATH = "/api/v1/categories";
    private static final String LABEL_FIELD = "label";
    private static final String NAME_FIELD = "name";
    private static final String CATEGORY_ID_FIELD = "categoryId";
    private static final String LABEL_1 = "label1";
    private static final String NAME_1 = "name1";
    private static final String LABEL_2 = "label2";
    private static final String NAME_2 = "name2";
    private static final String LABEL_3 = "label3";
    private static final String NAME_3 = "name3";

    private static final CategoryDTO CATEGORY_DTO = new CategoryDTO(
        LABEL_1,
        NAME_1,
        null
    );

    private static final CategoryDTO CATEGORY_DTO_2 = new CategoryDTO(
        LABEL_2,
        NAME_2,
        null
    );

    private static final UpdateCategoryRequest UPDATE_CATEGORY_REQUEST = new UpdateCategoryRequest(LABEL_3, NAME_3);

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void postCategories_ValidInput_ReturnsCreatedCategory() throws Exception {
        // when
        RequestBuilder postCategoryRequestBuilder = MockMvcRequestBuilders.post(CATEGORIES_API_PATH)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(CATEGORY_DTO));

        mockMvc.perform(postCategoryRequestBuilder)
            // then
            .andExpect(status().isOk())
            .andExpect(jsonPath(NAME_FIELD).value(NAME_1))
            .andExpect(jsonPath(LABEL_FIELD).value(LABEL_1))
            .andExpect(jsonPath(CATEGORY_ID_FIELD).isNotEmpty());
    }

    @Test
    public void getCategory_ValidInput_ReturnsCategory() throws Exception {
        // given
        RequestBuilder postCategoryRequestBuilder = MockMvcRequestBuilders.post(CATEGORIES_API_PATH)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(CATEGORY_DTO));

        String responseAsString = mockMvc.perform(postCategoryRequestBuilder)
            .andReturn()
            .getResponse()
            .getContentAsString();

        String categoryId = objectMapper.readTree(responseAsString).get(CATEGORY_ID_FIELD).asText();

        // when
        RequestBuilder getCategoryRequestBuilder = MockMvcRequestBuilders.get(CATEGORIES_API_PATH + "/" + categoryId)
            .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(getCategoryRequestBuilder)
            // then
            .andExpect(status().isOk())
            .andExpect(jsonPath(NAME_FIELD).value(NAME_1))
            .andExpect(jsonPath(LABEL_FIELD).value(LABEL_1))
            .andExpect(jsonPath(CATEGORY_ID_FIELD).value(categoryId));
    }

    @Test
    public void updateCategory_ValidInput_ReturnsUpdatedCategory() throws Exception {
        //given
        RequestBuilder postCategoryRequestBuilder = MockMvcRequestBuilders.post(CATEGORIES_API_PATH)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(CATEGORY_DTO));

        String responseAsString = mockMvc.perform(postCategoryRequestBuilder)
            .andReturn()
            .getResponse()
            .getContentAsString();

        String categoryId = objectMapper.readTree(responseAsString).get(CATEGORY_ID_FIELD).asText();

        // when
        RequestBuilder putCategoryRequestBuilder = MockMvcRequestBuilders.put(CATEGORIES_API_PATH + "/" + categoryId)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(UPDATE_CATEGORY_REQUEST));

        mockMvc.perform(putCategoryRequestBuilder)
            // then
            .andExpect(status().isOk())
            .andExpect(jsonPath(NAME_FIELD).value(NAME_3))
            .andExpect(jsonPath(LABEL_FIELD).value(LABEL_3))
            .andExpect(jsonPath(CATEGORY_ID_FIELD).value(categoryId));
    }

    @Test
    public void getCategories_ValidInput_ReturnsCategories() throws Exception {
        //given
        RequestBuilder postCategoryRequestBuilder1 = MockMvcRequestBuilders.post(CATEGORIES_API_PATH)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(CATEGORY_DTO));

        mockMvc.perform(postCategoryRequestBuilder1);

        RequestBuilder postCategoryRequestBuilder2 = MockMvcRequestBuilders.post(CATEGORIES_API_PATH)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(CATEGORY_DTO_2));

        mockMvc.perform(postCategoryRequestBuilder2);

        // when
        RequestBuilder getCategoriesRequestBuilder = MockMvcRequestBuilders.get(CATEGORIES_API_PATH)
            .contentType(MediaType.APPLICATION_JSON);

        String responseString = mockMvc.perform(getCategoriesRequestBuilder)
            .andExpect(status().isOk())
            .andReturn()
            .getResponse()
            .getContentAsString();

        List<CategoryDTO> testRelevantCategories = objectMapper.readValue(responseString, new TypeReference<List<CategoryDTO>>() {})
            .stream()
            .filter(c-> (c.label().equals(LABEL_1) && c.name().equals(NAME_1)) ||
                c.label().equals(LABEL_2) && c.name().equals(NAME_2))
            .collect(Collectors.toList());

        // then
        assertThat(testRelevantCategories).hasSizeGreaterThanOrEqualTo(2);
    }
}
