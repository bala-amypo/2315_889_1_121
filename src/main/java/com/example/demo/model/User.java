package com.example.demo.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fullName;

    @Column(unique = true)
    private String email;

    private String password;
    private String role = "VIEWER";

    private LocalDateTime createdAt = LocalDateTime.now();

   

    public Long getId() {
        return id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
repository
ExamRoomRepository.java
package com.example.demo.repository;

import com.example.demo.model.ExamRoom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ExamRoomRepository extends JpaRepository<ExamRoom, Long> {
    Optional<ExamRoom> findByRoomNumber(String roomNumber);
}
ExamSessionRepository.java
package com.example.demo.repository;

import com.example.demo.model.ExamSession;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExamSessionRepository extends JpaRepository<ExamSession, Long> {
}
