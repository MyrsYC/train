package com.train.business.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * Order creation DTO
 */
@Data
public class OrderCreateDTO implements Serializable {
    
    @NotNull(message = "User ID cannot be empty")
    private Long userId;
    
    @NotNull(message = "Train ID cannot be empty")
    private Long trainId;
    
    @NotBlank(message = "Passenger name cannot be empty")
    private String passengerName;
    
    @NotBlank(message = "ID card cannot be empty")
    private String idCard;
    
    @NotBlank(message = "Seat type cannot be empty")
    private String seatType; // BUSINESS, FIRST, SECOND
}
