package com.example.demo.entity;

import jakarta.persistence.column;
import jakarta.persistence.Id;

public class User{
    @Id
    private Long id;
    private String name;
    @coloumn(unique=tue)
    private String email;
    private String password;
    private String ADMIN;
    private String STAFF

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