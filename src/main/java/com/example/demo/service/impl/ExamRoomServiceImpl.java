package com.example.demo.service.impl;

import com.example.demo.exception.ApiException;
import com.example.demo.model.ExamRoom;
import com.example.demo.repository.ExamRoomRepository;
import com.example.demo.service.ExamRoomService;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ExamRoomServiceImpl implements ExamRoomService {
    private final ExamRoomRepository examRoomRepository;

    public ExamRoomServiceImpl(ExamRoomRepository examRoomRepository) {
        this.examRoomRepository = examRoomRepository;
    }

    @Override
    public ExamRoom addRoom(ExamRoom room) {
        if (examRoomRepository.findByRoomNumber(room.getRoomNumber()).isPresent()) {
            throw new ApiException("Room with number already exists");
        }
        
        if (room.getRows() != null && room.getRows() < 1) {
            throw new ApiException("Rows must be positive");
        }
        
        if (room.getRows() != null && room.getColumns() != null) {
            room.setCapacity(room.getRows() * room.getColumns());
        }
        
        return examRoomRepository.save(room);
    }

    @Override
    public List<ExamRoom> getAllRooms() {
        return examRoomRepository.findAll();
    }
}