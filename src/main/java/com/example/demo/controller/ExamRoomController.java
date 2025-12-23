package com.example.demo.controller;

import com.example.demo.model.ExamRoom;
import com.example.demo.service.ExamRoomService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rooms")
public class ExamRoomController {

    private final ExamRoomService service;

    public ExamRoomController(ExamRoomService service) {
        this.service = service;
    }

    @PostMapping
    public ExamRoom add(@RequestBody ExamRoom room) {
        return service.add(room);
    }

    @GetMapping
    public List<ExamRoom> all() {
        return service.all();
    }
}
