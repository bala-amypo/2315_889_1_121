package com.example.demo.controller;

import com.example.demo.model.SeatingPlan;
import com.example.demo.service.SeatingPlanService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@SecurityRequirement(name = "bearerAuth")
@RestController
@RequestMapping("/plans")
public class SeatingPlanController {

    private final SeatingPlanService service;

    public SeatingPlanController(SeatingPlanService service) {
        this.service = service;
    }

    @PostMapping("/{sessionId}")
    public ResponseEntity<SeatingPlan> generate(@PathVariable Long sessionId) {
        return ResponseEntity.ok(service.generatePlan(sessionId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<SeatingPlan> get(@PathVariable Long id) {
        return ResponseEntity.ok(service.getPlan(id));
    }

    // 🔥 IMPORTANT FIX HERE
    @GetMapping("/session/{sessionId}")
    public ResponseEntity<List<SeatingPlan>> list(@PathVariable Long sessionId) {
        return ResponseEntity.ok(service.getPlansBySession(sessionId));
    }
}
