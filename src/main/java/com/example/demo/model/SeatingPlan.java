package com.example.demo.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "seating_plans")
public class SeatingPlan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private ExamSession examSession;

    @ManyToOne
    private ExamRoom room;

    @Column(columnDefinition = "TEXT")
    private String arrangementJson;

    private LocalDateTime generatedAt;

    @PrePersist
    public void onCreate() {
        generatedAt = LocalDateTime.now();
    }

    public Long getId() {
         return id;
          }
    public void setExamSession(ExamSession examSession) {
         this.examSession = examSession; 
         }
    public void setRoom(ExamRoom room) { 
        this.room = room; 
        }
    public void setArrangementJson(String arrangementJson) {
         this.arrangementJson = arrangementJson; 
         }
}