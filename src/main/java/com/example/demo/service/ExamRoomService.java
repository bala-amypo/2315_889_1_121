package com.example.demo.service;

import com.example.demo.model.ExamRoom;
import java.util.List;

public interface ExamRoomService {

    ExamRoom addRoom(ExamRoom examRoom);

    List<ExamRoom> getAllRooms();

    void delete(Long id);
}
