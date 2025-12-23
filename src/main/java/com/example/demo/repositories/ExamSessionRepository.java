package com.example.demo.repository;

import com.example.demo.model.ExamSession;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExamSessionRepository extends JpaRepository<ExamSession, Long> {
}
SeatingPlanRepository.java
package com.example.demo.repository;

import com.example.demo.model.SeatingPlan;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SeatingPlanRepository extends JpaRepository<SeatingPlan, Long> {
}
