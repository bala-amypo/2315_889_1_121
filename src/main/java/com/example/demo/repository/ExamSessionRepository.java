package com.example.demo.repository;

import com.example.demo.model.ExamSession;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface ExamSessionRepository extends JpaRepository<ExamSession, Long> {
    List<ExamSession> findByExamDate(LocalDate date);
}
