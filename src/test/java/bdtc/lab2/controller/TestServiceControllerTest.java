package bdtc.lab2.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import bdtc.lab2.config.ServiceConf;
import bdtc.lab2.config.UtilsConf;
import bdtc.lab2.model.FlightEntity;
import bdtc.lab2.utils.EntityUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {ServiceConf.class, UtilsConf.class})
public class TestServiceControllerTest {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private MockMvc mvc;

    @Autowired
    private EntityUtils entityUtils;

    @Before
    public void init() {
        entityUtils.clearFlightEntitiesCache();
    }

    @After
    public void clear() {
        entityUtils.clearFlightEntitiesCache();
    }

    @Test
    public void get_should_returnFlightEntity_when_flightEntityExists() throws Exception {
        FlightEntity flightEntity = entityUtils.createAndSaveFlightEntity();
        String expectedJson = objectMapper.writeValueAsString(flightEntity);

        mvc.perform(get("/flight/get/" + flightEntity.getId()).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedJson));
    }
}
