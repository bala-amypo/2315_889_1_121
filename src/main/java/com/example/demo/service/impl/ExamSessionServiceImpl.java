package com.example.demo.service.impl;

import com.example.demo.exception.ApiException;
import com.example.demo.model.ExamSession;
import com.example.demo.repository.ExamSessionRepository;
import com.example.demo.repository.StudentRepository;
import com.example.demo.service.ExamSessionService;
import org.springframework.stereotype.Service;
import java.time.LocalDate;

@Service
public class ExamSessionServiceImpl implements ExamSessionService {
    private final ExamSessionRepository examSessionRepository;
    private final StudentRepository studentRepository;

    public ExamSessionServiceImpl(ExamSessionRepository examSessionRepository, StudentRepository studentRepository) {
        this.examSessionRepository = examSessionRepository;
        this.studentRepository = studentRepository;
    }

    @Override
    public ExamSession createSession(ExamSession session) {
        if (session.getExamDate().isBefore(LocalDate.now())) {
            throw new ApiException("Exam date cannot be in the past");
        }
        
        if (session.getStudents() == null || session.getStudents().isEmpty()) {
            throw new ApiException("Session must have at least 1 student");
        }
        
        return examSessionRepository.save(session);
    }

    @Override
    public ExamSession getSession(Long id) {
        return examSessionRepository.findById(id)
                .orElseThrow(() -> new ApiException("Session not found"));
    }
}