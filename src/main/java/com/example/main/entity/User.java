package com.example.main.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotEmpty;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long userId;

    @NotNull
    @NotEmpty
    private String name;

    @NotNull
    @NotEmpty
    private String upiId;

    @NotNull
    @NotEmpty
    private Double balance;

    @NotNull
    @NotEmpty
    private String phoneNumber;

    public User(String name, String upiId, Double balance, String phoneNumber) {
        this.name = name;
        this.upiId = upiId;
        this.balance = balance;
        this.phoneNumber = phoneNumber;
    }

    public User() {}

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUpiId() {
        return upiId;
    }

    public void setUpiId(String upiId) {
        this.upiId = upiId;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
