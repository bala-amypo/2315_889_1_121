package com.example.demo.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.Set;

@Entity
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class ExamSession {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String courseCode;
    private LocalDate examDate;
    private String examTime;

    @ManyToMany
    private Set<Student> students;
}
