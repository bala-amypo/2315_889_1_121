package com.example.demo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Id;

public class ExamRoom{
    @Id
    private Long id;
    @Column(unique=true)
    private String roomNumber;
    private Integer capacity;
    private Integer rows;
    public Long getId() {
        return id;
    }
    public String getRoomNumber() {
        return roomNumber;
    }
    public Integer getCapacity() {
        return capacity;
    }
    public Integer getRows() {
        return rows;
    }
    public Integer getColumns() {
        return columns;
    }
    private Integer columns;
}