package com.example.demo.service.impl;

import com.example.demo.exception.ApiException;
import com.example.demo.model.ExamRoom;
import com.example.demo.model.ExamSession;
import com.example.demo.model.SeatingPlan;
import com.example.demo.repository.ExamRoomRepository;
import com.example.demo.repository.ExamSessionRepository;
import com.example.demo.repository.SeatingPlanRepository;
import com.example.demo.service.SeatingPlanService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class SeatingPlanServiceImpl implements SeatingPlanService {

    private final ExamSessionRepository sessionRepository;
    private final SeatingPlanRepository planRepository;
    private final ExamRoomRepository roomRepository;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public SeatingPlan generatePlan(Long sessionId) {
        ExamSession session = sessionRepository.findById(sessionId)
                .orElseThrow(() -> new ApiException("Session not found: " + sessionId));

        int studentCount = session.getStudents().size();

        // Simple logic: find first room that fits
        List<ExamRoom> rooms = roomRepository.findAll();
        ExamRoom suitableRoom = rooms.stream()
                .filter(r -> r.getCapacity() >= studentCount)
                .findFirst()
                .orElseThrow(() -> new ApiException(
                        "No room found with sufficient capacity for " + studentCount + " students"));

        // Generate mock arrangement
        Map<String, String> arrangement = new HashMap<>();
        int i = 0;
        for (var student : session.getStudents()) {
            arrangement.put("Seat-" + (++i), student.getRollNumber());
        }

        String json = "{}";
        try {
            json = objectMapper.writeValueAsString(arrangement);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        SeatingPlan plan = SeatingPlan.builder()
                .examSession(session)
                .room(suitableRoom)
                .arrangementJson(json)
                .generatedAt(LocalDateTime.now())
                .build();

        return planRepository.save(plan);
    }

    @Override
    public SeatingPlan getPlan(Long id) {
        return planRepository.findById(id)
                .orElseThrow(() -> new ApiException("Plan not found: " + id));
    }

    @Override
    public List<SeatingPlan> getPlansBySession(Long sessionId) {
        return planRepository.findByExamSessionId(sessionId);
    }
}
