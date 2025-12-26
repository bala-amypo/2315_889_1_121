package com.example.demo.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "students")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(unique = true)
    private String rollNumber;
    
    private String name;
    private String department;
    @Column(name = "student_year")
    private Integer year;
    
    // Explicit getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getRollNumber() { return rollNumber; }
    public void setRollNumber(String rollNumber) { this.rollNumber = rollNumber; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }
    public Integer getYear() { return year; }
    public void setYear(Integer year) { this.year = year; }
    
    public static StudentBuilder builder() {
        return new StudentBuilder();
    }
    
    public static class StudentBuilder {
        private Long id;
        private String rollNumber;
        private String name;
        private String department;
        private Integer year;
        
        public StudentBuilder id(Long id) { this.id = id; return this; }
        public StudentBuilder rollNumber(String rollNumber) { this.rollNumber = rollNumber; return this; }
        public StudentBuilder name(String name) { this.name = name; return this; }
        public StudentBuilder department(String department) { this.department = department; return this; }
        public StudentBuilder year(Integer year) { this.year = year; return this; }
        
        public Student build() {
            Student student = new Student();
            student.setId(this.id);
            student.setRollNumber(this.rollNumber);
            student.setName(this.name);
            student.setDepartment(this.department);
            student.setYear(this.year);
            return student;
        }
    }
}