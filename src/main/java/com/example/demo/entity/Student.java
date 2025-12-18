package com.example.demo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Id;

public class Student{
    @Id
    private Long id;
    @Column(unique=true)
    private String rollNumber;
    private String name;
    private String department;
    private Integer year;

    public Student() {
    }
    public Student(Long id, String rollNumber, String name, String department, Integer year) {
        this.id = id;
        this.rollNumber = rollNumber;
        this.name = name;
        this.department = department;
        this.year = year;
    }
    public Long getId() {
        return id;
    }
    public String getRollNumber() {
        return rollNumber;
    }
    public String getName() {
        return name;
    }
    public String getDepartment() {
        return department;
    }
    public Integer getYear() {
        return year;
    }

}