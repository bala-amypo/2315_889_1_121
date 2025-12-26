package com.example.demo.controller;

import com.example.demo.model.ExamSession;
import com.example.demo.service.ExamSessionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@SecurityRequirement(name = "bearerAuth")
@RestController
@RequestMapping("/sessions")
public class ExamSessionController {

    private final ExamSessionService service;

    public ExamSessionController(ExamSessionService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<ExamSession> create(@RequestBody ExamSession session) {
        return ResponseEntity.ok(service.createSession(session));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ExamSession> get(@PathVariable Long id) {
        return ResponseEntity.ok(service.getSession(id));
    }
}
