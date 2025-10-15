package com.train.business.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * Train query DTO
 */
@Data
public class TrainQueryDTO implements Serializable {
    
    @NotBlank(message = "Start station cannot be empty")
    private String startStation;
    
    @NotBlank(message = "End station cannot be empty")
    private String endStation;
    
    @NotBlank(message = "Departure date cannot be empty")
    private String departureDate; // Format: yyyy-MM-dd
}
