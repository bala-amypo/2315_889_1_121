package com.example.demo.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class SeatingPlan {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private ExamSession examSession;

    @ManyToOne
    private ExamRoom room;

    @Column(columnDefinition = "TEXT")
    private String arrangementJson;

    private LocalDateTime generatedAt;
}
