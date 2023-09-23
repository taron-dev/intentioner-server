package sk.taron.intentioner.mvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;

@WebMvcTest(IntentionsController.class)
class IntentionsControllerTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

//    @BeforeEach
//    void setUp() {
//    }

    @Test
    void getAllIntentions() {
    }

    @Test
    void saveIntention() {
    }

    @Test
    void deleteIntention() {
    }

    @Test
    void getIntention() {
    }

    @Test
    void updateIntention() {
    }
}