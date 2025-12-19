public class ExamSession {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;


private String courseCode;
private LocalDate examDate;
private String examTime;


@ManyToMany
private List<Student> students;
}