package com.train.user.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

/**
 * User registration DTO
 */
@Data
public class UserRegisterDTO implements Serializable {
    
    @NotBlank(message = "Username cannot be empty")
    private String username;
    
    @NotBlank(message = "Password cannot be empty")
    private String password;
    
    @NotBlank(message = "Real name cannot be empty")
    private String realName;
    
    @NotBlank(message = "ID card cannot be empty")
    @Pattern(regexp = "^[1-9]\\d{5}(18|19|20)\\d{2}(0[1-9]|1[0-2])(0[1-9]|[12]\\d|3[01])\\d{3}[\\dXx]$", 
             message = "Invalid ID card format")
    private String idCard;
    
    @NotBlank(message = "Phone cannot be empty")
    @Pattern(regexp = "^1[3-9]\\d{9}$", message = "Invalid phone format")
    private String phone;
    
    private String email;
}
