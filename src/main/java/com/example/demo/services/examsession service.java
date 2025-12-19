public class ExamSessionService {
private final ExamSessionRepository repo;
private final StudentRepository studentRepo;


public ExamSessionService(ExamSessionRepository repo, StudentRepository studentRepo) {
this.repo = repo;
this.studentRepo = studentRepo;
}


public ExamSession createSession(ExamSession session) {
if (session.getExamDate().isBefore(LocalDate.now()))
throw new ApiException("past");
if (session.getStudents() == null || session.getStudents().isEmpty())
throw new ApiException("at least 1 student");
return repo.save(session);
}


public ExamSession getSession(Long id) {
return repo.findById(id)
.orElseThrow(() -> new ApiException("session not found"));
}
}