package com.example.demo.repository;

import com.example.demo.model.SeatingPlan;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SeatingPlanRepository extends JpaRepository<SeatingPlan, Long> {
}