package com.indocyber.dto;

import com.indocyber.validation.Compare;
import com.indocyber.validation.RegisterAdminDtoPasswordMatcher;
import com.indocyber.validation.UniqueUsername;

import javax.validation.constraints.NotNull;

@Compare(message="Confirm Password does not match!", firstField="password", secondField="passwordConfirmation")
//@RegisterAdminDtoPasswordMatcher(message = "Confirm Password does not match!")
public class RegisterAdminDto {

    @NotNull(message = "Username is required!")
    @UniqueUsername(message = "Username already exists")
    private String username;

    @NotNull(message = "Password is required!")
    private String password;

//    @NotNull(message = "Confirm Password is required!")
    private String passwordConfirmation;

    private String role;

    public RegisterAdminDto(){}

    public RegisterAdminDto(String username, String password, String passwordConfirmation, String role) {
        this.username = username;
        this.password = password;
        this.passwordConfirmation = passwordConfirmation;
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasswordConfirmation() {
        return passwordConfirmation;
    }

    public void setPasswordConfirmation(String passwordConfirmation) {
        this.passwordConfirmation = passwordConfirmation;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
