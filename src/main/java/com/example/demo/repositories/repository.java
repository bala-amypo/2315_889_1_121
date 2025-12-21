public interface UserRepository extends JpaRepository<User, Long> {
Optional<User> findByEmail(String email);
}


public interface StudentRepository extends JpaRepository<Student, Long> {
Optional<Student> findByRollNumber(String rollNumber);
}


public interface ExamRoomRepository extends JpaRepository<ExamRoom, Long> {
Optional<ExamRoom> findByRoomNumber(String roomNumber);
List<ExamRoom> findByCapacityGreaterThanEqual(Integer capacity);
}


public interface ExamSessionRepository extends JpaRepository<ExamSession, Long> {
List<ExamSession> findByExamDate(LocalDate date);
}


public interface SeatingPlanRepository extends JpaRepository<SeatingPlan, Long> {
List<SeatingPlan> findByExamSessionId(Long sessionId);
}