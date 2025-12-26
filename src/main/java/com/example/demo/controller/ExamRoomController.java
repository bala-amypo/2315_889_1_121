package com.example.demo.controller;

import com.example.demo.model.ExamRoom;
import com.example.demo.service.ExamRoomService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@SecurityRequirement(name = "bearerAuth")
@RestController
@RequestMapping("/rooms")
public class ExamRoomController {

    private final ExamRoomService service;

    public ExamRoomController(ExamRoomService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<ExamRoom> add(@RequestBody ExamRoom room) {
        return ResponseEntity.ok(service.addRoom(room));
    }

    // 🔥 IMPORTANT FIX HERE
    @GetMapping
    public ResponseEntity<List<ExamRoom>> list() {
        return ResponseEntity.ok(service.getAllRooms());
    }
}
