package com.example.demo.model;

import jakarta.persistence.*;

@Entity
@Table(name = "students")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String rollNumber;

    private String name;
    private String department;
    private Integer year;

    public Long getId() {
         return id;
          }
    public String getRollNumber() {
         return rollNumber; 
         }
    public void setRollNumber(String rollNumber) { 
    this.rollNumber = rollNumber; 
    }
    public String getName() {
         return name; 
         }
    public void setName(String name) { 
        this.name = name; 
        }
    public String getDepartment() {
         return department;
          }
    public void setDepartment(String department) {
         this.department = department; 
         }
    public Integer getYear() {
         return year; 
         }
    public void setYear(Integer year) {
         this.year = year; 
         }
}