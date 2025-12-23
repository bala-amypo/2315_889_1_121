// package com.example.demo.service;

// import com.example.demo.exception.ApiException;
// import com.example.demo.model.ExamRoom;
// import com.example.demo.model.ExamSession;
// import com.example.demo.model.SeatingPlan;
// import com.example.demo.repository.ExamRoomRepository;
// import com.example.demo.repository.ExamSessionRepository;
// import com.example.demo.repository.SeatingPlanRepository;
// import org.springframework.stereotype.Service;

// @Service
// public class SeatingPlanServiceImpl implements SeatingPlanService {

//     private final ExamSessionRepository sessionRepo;
//     private final ExamRoomRepository roomRepo;
//     private final SeatingPlanRepository planRepo;

//     public SeatingPlanServiceImpl(
//             ExamSessionRepository s,
//             ExamRoomRepository r,
//             SeatingPlanRepository p) {
//         this.sessionRepo = s;
//         this.roomRepo = r;
//         this.planRepo = p;
//     }

//     @Override
//     public SeatingPlan generate(Long sessionId) {

//         ExamSession session = sessionRepo.findById(sessionId)
//                 .orElseThrow(() -> new ApiException("session not found"));

//         int count = session.getStudents().size();

//         ExamRoom room = roomRepo.findAll().stream()
//                 .filter(r -> r.getCapacity() >= count)
//                 .findFirst()
//                 .orElseThrow(() -> new ApiException("no room"));

//         SeatingPlan plan = new SeatingPlan();
//         plan.setExamSession(session);
//         plan.setRoom(room);
//         plan.setArrangementJson("{\"students\":" + count + "}");

//         return planRepo.save(plan);
//     }
// }
