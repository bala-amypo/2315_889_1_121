package com.example.demo.entity;

import jakarta.persistence.column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

public class User{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @coloumn(unique=tue)
    private String email;
    private String password;
    private String ADMIN;
    private String STAFF;

    public User() {
    }
    public User(Long id, String name, String email, String password, String aDMIN, String sTAFF) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        ADMIN = aDMIN;
        STAFF = sTAFF;
    }
    public Long getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public String getEmail() {
        return email;
    }
    public String getPassword() {
        return password;
    }
    public String getADMIN() {
        return ADMIN;
    }
    public String getSTAFF() {
        return STAFF;
    }
}