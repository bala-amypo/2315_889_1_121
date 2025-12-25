package com.example.demo.service.impl;

import com.example.demo.exception.ApiException;
import com.example.demo.model.ExamSession;
import com.example.demo.model.Student;
import com.example.demo.repository.ExamSessionRepository;
import com.example.demo.repository.StudentRepository;
import com.example.demo.service.ExamSessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ExamSessionServiceImpl implements ExamSessionService {

    private final ExamSessionRepository examSessionRepository;
    private final StudentRepository studentRepository;

    @Override
    public ExamSession createSession(ExamSession session) {
        if (session.getExamDate().isBefore(LocalDate.now())) {
            throw new ApiException("Exam date cannot be in the past");
        }
        if (session.getStudents() == null || session.getStudents().isEmpty()) {
            throw new ApiException("At least 1 student required");
        }

        // Ensure students are managed entities if they exist, or save them if new?
        // Test `test53_session_creation_with_existing_students` implies we might pass
        // existing students.
        // Usually we reload them from DB to ensure they attach correctly if detached.
        Set<Student> managedStudents = new HashSet<>();
        for (Student s : session.getStudents()) {
            if (s.getId() != null) {
                managedStudents.add(studentRepository.findById(s.getId()).orElse(s));
            } else {
                managedStudents.add(s);
            }
        }
        session.setStudents(managedStudents);

        return examSessionRepository.save(session);
    }

    @Override
    public ExamSession getSession(Long id) {
        return examSessionRepository.findById(id)
                .orElseThrow(() -> new ApiException("Session not found with id: " + id));
    }
}
