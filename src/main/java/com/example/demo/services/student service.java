public class StudentService {
private final StudentRepository repo;


public StudentService(StudentRepository repo) {
this.repo = repo;
}


public Student addStudent(Student s) {
if (s.getYear() < 1 || s.getYear() > 5)
throw new ApiException("year");
return repo.save(s);
}


public List<Student> getAllStudents() {
return repo.findAll();
}
}