package com.example.demo.service.impl;

import com.example.demo.exception.ApiException;
import com.example.demo.model.ExamRoom;
import com.example.demo.model.ExamSession;
import com.example.demo.model.SeatingPlan;
import com.example.demo.repository.ExamRoomRepository;
import com.example.demo.repository.ExamSessionRepository;
import com.example.demo.repository.SeatingPlanRepository;
import com.example.demo.service.SeatingPlanService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class SeatingPlanServiceImpl implements SeatingPlanService {

    private final ExamSessionRepository sessionRepo;
    private final SeatingPlanRepository planRepo;
    private final ExamRoomRepository roomRepo;

    public SeatingPlanServiceImpl(ExamSessionRepository sessionRepo,
                                  SeatingPlanRepository planRepo,
                                  ExamRoomRepository roomRepo) {
        this.sessionRepo = sessionRepo;
        this.planRepo = planRepo;
        this.roomRepo = roomRepo;
    }

    @Override
    public SeatingPlan generatePlan(Long sessionId) {

        ExamSession session = sessionRepo.findById(sessionId)
                .orElseThrow(() -> new ApiException("Session not found"));

        if (session.getStudents() == null || session.getStudents().isEmpty()) {
            throw new ApiException("No students in session");
        }

        int studentCount = session.getStudents().size();

        List<ExamRoom> rooms = roomRepo.findAll();
        if (rooms.isEmpty()) {
            throw new ApiException("No room available");
        }

        ExamRoom selectedRoom = null;

        // 🔥 NO STREAMS — MANUAL CAPACITY CHECK
        for (ExamRoom room : rooms) {
            if (room.getCapacity() >= studentCount) {
                selectedRoom = room;
                break;
            }
        }

        if (selectedRoom == null) {
            throw new ApiException("No room available");
        }

        // 🔥 Seat-wise arrangement: Seat 1, Seat 2, ...
        StringBuilder arrangement = new StringBuilder("{");
        int seatNo = 1;

        for (var student : session.getStudents()) {
            arrangement.append("\"Seat ")
                       .append(seatNo++)
                       .append("\":\"")
                       .append(student.getRollNumber())
                       .append("\",");
        }

        // remove last comma
        arrangement.deleteCharAt(arrangement.length() - 1);
        arrangement.append("}");

        SeatingPlan plan = new SeatingPlan();
        plan.setExamSession(session);
        plan.setRoom(selectedRoom);
        plan.setGeneratedAt(LocalDateTime.now());
        plan.setArrangementJson(arrangement.toString());

        return planRepo.save(plan);
    }

    @Override
    public SeatingPlan getPlan(Long id) {
        return planRepo.findById(id)
                .orElseThrow(() -> new ApiException("Plan not found"));
    }

    @Override
    public List<SeatingPlan> getPlansBySession(Long sessionId) {
        return planRepo.findByExamSessionId(sessionId);
    }
}
