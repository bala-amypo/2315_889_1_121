package com.example.demo.service.impl;

import com.example.demo.exception.ApiException;
import com.example.demo.model.*;
import com.example.demo.repository.*;
import com.example.demo.service.SeatingPlanService;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class SeatingPlanServiceImpl implements SeatingPlanService {
    private final ExamSessionRepository examSessionRepository;
    private final SeatingPlanRepository seatingPlanRepository;
    private final ExamRoomRepository examRoomRepository;

    public SeatingPlanServiceImpl(ExamSessionRepository examSessionRepository, 
                                  SeatingPlanRepository seatingPlanRepository, 
                                  ExamRoomRepository examRoomRepository) {
        this.examSessionRepository = examSessionRepository;
        this.seatingPlanRepository = seatingPlanRepository;
        this.examRoomRepository = examRoomRepository;
    }

    @Override
    public SeatingPlan generatePlan(Long sessionId) {
        ExamSession session = examSessionRepository.findById(sessionId)
                .orElseThrow(() -> new ApiException("Session not found"));
        
        List<ExamRoom> rooms = examRoomRepository.findAll();
        if (rooms.isEmpty()) {
            throw new ApiException("No room available");
        }
        
        ExamRoom selectedRoom = rooms.get(0); // Use first available room
        
        // Generate simple JSON arrangement
        StringBuilder arrangement = new StringBuilder("{");
        int pos = 0;
        for (Student student : session.getStudents()) {
            if (pos > 0) arrangement.append(",");
            arrangement.append("\"").append(pos).append("\":\"").append(student.getRollNumber()).append("\"");
            pos++;
        }
        arrangement.append("}");
        
        SeatingPlan plan = SeatingPlan.builder()
                .examSession(session)
                .room(selectedRoom)
                .arrangementJson(arrangement.toString())
                .generatedAt(LocalDateTime.now())
                .build();
        
        return seatingPlanRepository.save(plan);
    }

    @Override
    public SeatingPlan getPlan(Long id) {
        return seatingPlanRepository.findById(id)
                .orElseThrow(() -> new ApiException("Plan not found"));
    }

    @Override
    public List<SeatingPlan> getPlansBySession(Long sessionId) {
        return seatingPlanRepository.findByExamSessionId(sessionId);
    }
}