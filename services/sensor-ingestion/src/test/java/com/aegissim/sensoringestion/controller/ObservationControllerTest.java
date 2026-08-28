package com.aegissim.sensoringestion.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ObservationController.class)
class ObservationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldAcceptValidObservation() throws Exception {

        String json = """
                {
                  "sensorId": "RADAR-SIM-01",
                  "targetId": "TARGET-001",
                  "x": 120.5,
                  "y": 347.8,
                  "velocityX": 12.3,
                  "velocityY": -4.1,
                  "confidence": 0.94
                }
                """;

        mockMvc.perform(
                post("/api/v1/observations")
                        .contentType("application/json")
                        .content(json)
        )
        .andExpect(status().isCreated());
    }

    @Test
    void shouldRejectInvalidObservation() throws Exception {

        String json = """
                {
                  "sensorId": "",
                  "targetId": "TARGET-001",
                  "x": 120.5,
                  "y": 347.8,
                  "velocityX": 12.3,
                  "velocityY": -4.1,
                  "confidence": 2.0
                }
                """;

        mockMvc.perform(
                post("/api/v1/observations")
                        .contentType("application/json")
                        .content(json)
        )
        .andExpect(status().isBadRequest());
    }
}