package com.example.demo.service;

import com.example.demo.model.ExamSession;

public interface ExamSessionService {
    ExamSession createSession(ExamSession session);

    ExamSession getSession(Long id);
}
