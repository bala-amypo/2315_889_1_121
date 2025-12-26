package com.example.demo.service;

import java.util.List;

import com.example.demo.model.SeatingPlan;

public interface SeatingPlanService{
    SeatingPlan generatePlan(Long sessionId);
    SeatingPlan getPlan(Long planId);
    List<SeatingPlan> getPlansBySession(Long sessionId);
}