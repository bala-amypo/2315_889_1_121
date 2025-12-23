package com.example.demo.controller;
import com.example.demo.model.Student;
import com.example.demo.service.StudentService;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RestController
@RequestMapping("/students")
public class StudentController {

    private final StudentService service;    
    public StudentController(StudentService service) {
        this.service = service;
    }
    @PostMapping
    public Student add(@RequestBody Student student) {
        return service.add(student);
    }
    @GetMapping
    public List<Student> all() {
        return service.all();
    }
}
