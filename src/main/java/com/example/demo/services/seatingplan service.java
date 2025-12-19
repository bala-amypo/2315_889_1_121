public class SeatingPlanService {
private final ExamSessionRepository sessionRepo;
private final SeatingPlanRepository planRepo;
private final ExamRoomRepository roomRepo;


public SeatingPlanService(ExamSessionRepository s, SeatingPlanRepository p, ExamRoomRepository r) {
this.sessionRepo = s;
this.planRepo = p;
this.roomRepo = r;
}


public SeatingPlan generatePlan(Long sessionId) {
ExamSession session = sessionRepo.findById(sessionId)
.orElseThrow(() -> new ApiException("session not found"));


int count = session.getStudents().size();
ExamRoom room = roomRepo.findByCapacityGreaterThanEqual(count)
.stream().findFirst()
.orElseThrow(() -> new ApiException("no room"));


SeatingPlan plan = new SeatingPlan();
plan.setExamSession(session);
plan.setRoom(room);
plan.setArrangementJson("{students:" + count + "}");


return planRepo.save(plan);
}


public SeatingPlan getPlan(Long id) {
return planRepo.findById(id)
.orElseThrow(() -> new ApiException("plan not found"));
}


public List<SeatingPlan> getPlansBySession(Long sessionId) {
return planRepo.findByExamSessionId(sessionId);
}
}