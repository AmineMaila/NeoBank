package com.neobank.backend.dto;

import com.neobank.backend.validators.FieldMatch;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@FieldMatch(first = "password", second = "confirmPassword")
public class RegisterRequest {
    @NotBlank(message = "Username is required")
    @Size(min = 3, max = 20)
    private String username;

    @NotBlank(message = "password is required")
    @Size(min = 1, max = 100)
    private String password;

    private String confirmPassword;

    public RegisterRequest(String username, String password, String confirmPassword) {
        this.username = username;
        this.password = password;
        this.confirmPassword = confirmPassword;
    }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getConfirmPassword() { return confirmPassword; }
    public void setConfirmPassword(String confirmPassword) { this.confirmPassword = confirmPassword; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
}
