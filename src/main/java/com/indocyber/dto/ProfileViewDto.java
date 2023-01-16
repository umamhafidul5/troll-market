package com.indocyber.dto;

import com.indocyber.entity.Transaction;

import java.math.BigDecimal;
import java.util.List;

public class ProfileViewDto {

    private String firstName;

    private String lastName;

    private String fullName;

    private String role;

    private String address;

    private BigDecimal balance;

    private List<TransactionViewDto> transactionViewDtoList;


    public ProfileViewDto(){}

    public ProfileViewDto(String firstName, String lastName, String role, String address, BigDecimal balance, List<TransactionViewDto> transactionViewDtoList) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.fullName = firstName+" "+lastName;
        this.role = role;
        this.address = address;
        this.balance = balance;
        this.transactionViewDtoList = transactionViewDtoList;
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

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public List<TransactionViewDto> getTransactionViewDtoList() {
        return transactionViewDtoList;
    }

    public void setTransactionViewDtoList(List<TransactionViewDto> transactionViewDtoList) {
        this.transactionViewDtoList = transactionViewDtoList;
    }
}
