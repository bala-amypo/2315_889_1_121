// package com.example.demo.model;

// import jakarta.persistence.*;
// import java.time.LocalDate;
// import java.util.Set;

// @Entity
// @Table(name = "exam_sessions")
// public class ExamSession {

//     @Id
//     @GeneratedValue(strategy = GenerationType.IDENTITY)
//     private Long id;

//     private String courseCode;
//     private LocalDate examDate;
//     private String examTime;

//     @ManyToMany
//     private Set<Student> students;

//     public Long getId() { 
//     return id; 
//     }
//     public String getCourseCode() { 
//     return courseCode; 
//     }
//     public void setCourseCode(String courseCode) {
//      this.courseCode = courseCode; 
//      }
//     public LocalDate getExamDate() {
//      return examDate;
//       }
//     public void setExamDate(LocalDate examDate) {
//      this.examDate = examDate; 
//      }
//     public String getExamTime() {
//      return examTime;
//       }
//     public void setExamTime(String examTime) { 
//     this.examTime = examTime; 
//     }
//     public Set<Student> getStudents() {
//      return students; 
//      }
//     public void setStudents(Set<Student> students) {
//      this.students = students; 
//      }
// }