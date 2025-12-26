package com.example.demo.service.impl;

import com.example.demo.exception.ApiException;
import com.example.demo.model.Student;
import com.example.demo.repository.StudentRepository;
import com.example.demo.service.StudentService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {

    private final StudentRepository repo;

    public StudentServiceImpl(StudentRepository repo) {
        this.repo = repo;
    }

    @Override
    public Student addStudent(Student student) {
        if (student.getRollNumber() == null || student.getYear() == null) {
            throw new ApiException("Invalid student data");
        }
        if (student.getYear() < 1 || student.getYear() > 5) {
            throw new ApiException("Invalid year");
        }
        repo.findByRollNumber(student.getRollNumber())
                .ifPresent(s -> { throw new ApiException("Student exists"); });
        return repo.save(student);
    }

    @Override
    public List<Student> getAllStudents() {
        return repo.findAll();
    }
}
