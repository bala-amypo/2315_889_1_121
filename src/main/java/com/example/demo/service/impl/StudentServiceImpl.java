package com.example.demo.service.impl;

import com.example.demo.exception.ApiException;
import com.example.demo.model.Student;
import com.example.demo.repository.StudentRepository;
import com.example.demo.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;

    @Override
    public Student addStudent(Student student) {
        if (studentRepository.findByRollNumber(student.getRollNumber()).isPresent()) {
            throw new ApiException("Student with roll number " + student.getRollNumber() + " already exists");
        }
        if (student.getYear() != null && student.getYear() > 4) {
            throw new ApiException("Invalid year: " + student.getYear());
        }
        return studentRepository.save(student);
    }

    @Override
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }
}
