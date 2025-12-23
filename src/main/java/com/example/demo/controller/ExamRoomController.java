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
    @GetMapping("/{id}")
    public ExamRoom getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @PutMapping("/{id}")
    public ExamRoom update(@PathVariable Long id, @RequestBody ExamRoom room) {
        return service.update(id, room);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
