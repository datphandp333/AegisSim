package com.aegissim.sensoringestion.controller;

import com.aegissim.sensoringestion.model.SensorObservation;
import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/observations")
public class ObservationController {

    @PostMapping
    public ResponseEntity<Map<String, Object>> receiveObservation(
            @Valid @RequestBody SensorObservation observation) {

        return ResponseEntity.status(HttpStatus.CREATED).body(
                Map.of(
                        "message", "Observation accepted",
                        "sensorId", observation.getSensorId(),
                        "targetId", observation.getTargetId()
                )
        );
    }
}