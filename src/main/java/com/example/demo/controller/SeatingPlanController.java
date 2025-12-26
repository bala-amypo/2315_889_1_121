package com.example.demo.controller;

import com.example.demo.model.SeatingPlan;
import com.example.demo.service.SeatingPlanService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/seating-plans")
public class SeatingPlanController {
    private final SeatingPlanService seatingPlanService;

    public SeatingPlanController(SeatingPlanService seatingPlanService) {
        this.seatingPlanService = seatingPlanService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<SeatingPlan> get(@PathVariable Long id) {
        return ResponseEntity.ok(seatingPlanService.getPlan(id));
    }

    @GetMapping("/session/{sessionId}")
    public ResponseEntity<List<SeatingPlan>> list(@PathVariable Long sessionId) {
        return ResponseEntity.ok(seatingPlanService.getPlansBySession(sessionId));
    }
}