public class ExamRoomService {
private final ExamRoomRepository repo;


public ExamRoomService(ExamRoomRepository repo) {
this.repo = repo;
}


public ExamRoom addRoom(ExamRoom room) {
if (repo.findByRoomNumber(room.getRoomNumber()).isPresent())
throw new ApiException("exists");
return repo.save(room);
}


public List<ExamRoom> getAllRooms() {
return repo.findAll();
}
}