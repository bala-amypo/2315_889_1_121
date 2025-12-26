package com.example.demo.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.util.Set;

@Entity
@Table(name = "exam_sessions")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExamSession {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String courseCode;
    private LocalDate examDate;
    private String examTime;
    
    @ManyToMany
    @JoinTable(
        name = "session_students",
        joinColumns = @JoinColumn(name = "session_id"),
        inverseJoinColumns = @JoinColumn(name = "student_id")
    )
    private Set<Student> students;
    
    // Explicit getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getCourseCode() { return courseCode; }
    public void setCourseCode(String courseCode) { this.courseCode = courseCode; }
    public LocalDate getExamDate() { return examDate; }
    public void setExamDate(LocalDate examDate) { this.examDate = examDate; }
    public String getExamTime() { return examTime; }
    public void setExamTime(String examTime) { this.examTime = examTime; }
    public Set<Student> getStudents() { return students; }
    public void setStudents(Set<Student> students) { this.students = students; }
    
    public static ExamSessionBuilder builder() {
        return new ExamSessionBuilder();
    }
    
    public static class ExamSessionBuilder {
        private Long id;
        private String courseCode;
        private LocalDate examDate;
        private String examTime;
        private Set<Student> students;
        
        public ExamSessionBuilder id(Long id) { this.id = id; return this; }
        public ExamSessionBuilder courseCode(String courseCode) { this.courseCode = courseCode; return this; }
        public ExamSessionBuilder examDate(LocalDate examDate) { this.examDate = examDate; return this; }
        public ExamSessionBuilder examTime(String examTime) { this.examTime = examTime; return this; }
        public ExamSessionBuilder students(Set<Student> students) { this.students = students; return this; }
        
        public ExamSession build() {
            ExamSession session = new ExamSession();
            session.setId(this.id);
            session.setCourseCode(this.courseCode);
            session.setExamDate(this.examDate);
            session.setExamTime(this.examTime);
            session.setStudents(this.students);
            return session;
        }
    }
}