package com.example.demo.model;

import java.util.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "students")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Student {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    
    @Column(unique=true, nullable = false)
    private String rollNumber;
    
    private String name;
    private String department;
    private Integer year;

    @Builder.Default
    @ManyToMany(mappedBy = "students")
    @JsonIgnore
    private Set<ExamSession> examSessions = new HashSet<>();
}
