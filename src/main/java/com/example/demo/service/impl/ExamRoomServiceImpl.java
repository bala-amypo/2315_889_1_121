package com.example.demo.service.impl;

import com.example.demo.exception.ApiException;
import com.example.demo.model.ExamRoom;
import com.example.demo.repository.ExamRoomRepository;
import com.example.demo.service.ExamRoomService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExamRoomServiceImpl implements ExamRoomService {

    private final ExamRoomRepository repo;

    public ExamRoomServiceImpl(ExamRoomRepository repo) {
        this.repo = repo;
    }

    @Override
    public ExamRoom addRoom(ExamRoom room) {
        if (room.getRows() == null || room.getColumns() == null ||
                room.getRows() <= 0 || room.getColumns() <= 0) {
            throw new ApiException("Invalid room dimensions");
        }

        repo.findByRoomNumber(room.getRoomNumber())
                .ifPresent(r -> { throw new ApiException("Room exists"); });

        room.ensureCapacityMatches();
        return repo.save(room);
    }

    @Override
    public List<ExamRoom> getAllRooms() {
        return repo.findAll();
    }
}
