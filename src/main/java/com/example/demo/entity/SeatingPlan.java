public class SeatingPlan {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;


@ManyToOne
private ExamSession examSession;


@ManyToOne
private ExamRoom room;


@Column(columnDefinition = "TEXT")
private String arrangementJson;


private LocalDateTime generatedAt;


@PrePersist
public void setTime() {
generatedAt = LocalDateTime.now();
}
}