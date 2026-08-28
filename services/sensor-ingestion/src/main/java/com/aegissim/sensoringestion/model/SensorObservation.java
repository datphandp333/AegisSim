package com.aegissim.sensoringestion.model;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class SensorObservation {

    @NotBlank(message = "sensorId is required")
    private String sensorId;

    @NotBlank(message = "targetId is required")
    private String targetId;

    @NotNull(message = "x position is required")
    private Double x;

    @NotNull(message = "y position is required")
    private Double y;

    @NotNull(message = "velocityX is required")
    private Double velocityX;

    @NotNull(message = "velocityY is required")
    private Double velocityY;

    @NotNull(message = "confidence is required")
    @Min(value = 0, message = "confidence cannot be below 0")
    @Max(value = 1, message = "confidence cannot be above 1")
    private Double confidence;

    public String getSensorId() {
        return sensorId;
    }

    public void setSensorId(String sensorId) {
        this.sensorId = sensorId;
    }

    public String getTargetId() {
        return targetId;
    }

    public void setTargetId(String targetId) {
        this.targetId = targetId;
    }

    public Double getX() {
        return x;
    }

    public void setX(Double x) {
        this.x = x;
    }

    public Double getY() {
        return y;
    }

    public void setY(Double y) {
        this.y = y;
    }

    public Double getVelocityX() {
        return velocityX;
    }

    public void setVelocityX(Double velocityX) {
        this.velocityX = velocityX;
    }

    public Double getVelocityY() {
        return velocityY;
    }

    public void setVelocityY(Double velocityY) {
        this.velocityY = velocityY;
    }

    public Double getConfidence() {
        return confidence;
    }

    public void setConfidence(Double confidence) {
        this.confidence = confidence;
    }
}