package com.example.demo.service; // Ensure this is correct

import com.example.demo.exception.ApiException;
import com.example.demo.model.Student;
import com.example.demo.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {

    private final StudentRepository repo;

    public StudentServiceImpl(StudentRepository repo) {
        this.repo = repo;
    }

    @Override
    public Student add(Student s) {
        if (s.getRollNumber() == null)
            throw new ApiException("roll number missing");

        if (repo.findByRollNumber(s.getRollNumber()).isPresent())
            throw new ApiException("exists");

        if (s.getYear() < 1 || s.getYear() > 5)
            throw new ApiException("year");

        return repo.save(s);
    }

    @Override
    public List<Student> all() {
        return repo.findAll();
    }
}
