package com.example.demo.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "seating_plans")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SeatingPlan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "exam_session_id")
    private ExamSession examSession;
    
    @ManyToOne
    @JoinColumn(name = "exam_room_id")
    private ExamRoom room;
    
    @Column(columnDefinition = "TEXT")
    private String arrangementJson;
    
    private LocalDateTime generatedAt;
    
    // Explicit getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public ExamSession getExamSession() { return examSession; }
    public void setExamSession(ExamSession examSession) { this.examSession = examSession; }
    public ExamRoom getRoom() { return room; }
    public void setRoom(ExamRoom room) { this.room = room; }
    public String getArrangementJson() { return arrangementJson; }
    public void setArrangementJson(String arrangementJson) { this.arrangementJson = arrangementJson; }
    public LocalDateTime getGeneratedAt() { return generatedAt; }
    public void setGeneratedAt(LocalDateTime generatedAt) { this.generatedAt = generatedAt; }
    
    public static SeatingPlanBuilder builder() {
        return new SeatingPlanBuilder();
    }
    
    public static class SeatingPlanBuilder {
        private Long id;
        private ExamSession examSession;
        private ExamRoom room;
        private String arrangementJson;
        private LocalDateTime generatedAt;
        
        public SeatingPlanBuilder id(Long id) { this.id = id; return this; }
        public SeatingPlanBuilder examSession(ExamSession examSession) { this.examSession = examSession; return this; }
        public SeatingPlanBuilder room(ExamRoom room) { this.room = room; return this; }
        public SeatingPlanBuilder arrangementJson(String arrangementJson) { this.arrangementJson = arrangementJson; return this; }
        public SeatingPlanBuilder generatedAt(LocalDateTime generatedAt) { this.generatedAt = generatedAt; return this; }
        
        public SeatingPlan build() {
            SeatingPlan plan = new SeatingPlan();
            plan.setId(this.id);
            plan.setExamSession(this.examSession);
            plan.setRoom(this.room);
            plan.setArrangementJson(this.arrangementJson);
            plan.setGeneratedAt(this.generatedAt);
            return plan;
        }
    }
}