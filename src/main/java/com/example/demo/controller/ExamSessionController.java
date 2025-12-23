package com.example.demo.controller;

import com.example.demo.model.ExamSession;
import com.example.demo.service.ExamSessionService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/sessions")
public class ExamSessionController {

    private final ExamSessionService service;

    public ExamSessionController(ExamSessionService service) {
        this.service = service;
    }

    @PostMapping
    public ExamSession create(@RequestBody ExamSession session) {
        return service.create(session);
    }

    @GetMapping("/{id}")
    public ExamSession get(@PathVariable Long id) {
        return service.get(id);
    }
}
