package com.TaskManagement.Application.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequest {
    @NotBlank(message = "username is empty")
    private String username;
    @Email(message = "Invalid email format")
    @NotBlank(message = "email is empty")
    private String email;
    @NotBlank(message = "password is empty")
    private String password;

}
