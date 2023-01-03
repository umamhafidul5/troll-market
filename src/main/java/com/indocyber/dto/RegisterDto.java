package com.indocyber.dto;

import com.indocyber.validation.Compare;
import com.indocyber.validation.UniqueUsername;

import javax.validation.constraints.NotNull;

@Compare(message="Password is not matched.", firstField="password", secondField="passwordConfirmation")
public class RegisterDto {

    @NotNull(message = "Username is required!")
    @UniqueUsername(message = "Username already exists")
    private String username;

    @NotNull(message = "Password is required!")
    private String password;

    private String passwordConfirmation;

    private String role;

    @NotNull(message = "Firstname is required!")
    private String firstName;

    @NotNull(message = "Lastname is required!")
    private String lastName;

    @NotNull(message = "Address is required!")
    private String address;

    public RegisterDto(){}

    public RegisterDto(String username, String password, String passwordConfirmation, String role, String firstName, String lastName, String address) {
        this.username = username;
        this.password = password;
        this.passwordConfirmation = passwordConfirmation;
        this.role = role;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
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

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
