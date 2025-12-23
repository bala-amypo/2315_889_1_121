package com.example.demo.service.impl;

import com.example.demo.model.ExamRoom;
import com.example.demo.repository.ExamRoomRepository;
import com.example.demo.service.ExamRoomService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExamRoomServiceImpl implements ExamRoomService {

    private final ExamRoomRepository repository;

    public ExamRoomServiceImpl(ExamRoomRepository repository) {
        this.repository = repository;
    }

    @Override
    public ExamRoom add(ExamRoom room) {
        return repository.save(room);
    }

    @Override
    public List<ExamRoom> all() {
        return repository.findAll();
    }

    @Override
    public ExamRoom getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("ExamRoom not found with id " + id));
    }

    @Override
    public ExamRoom update(Long id, ExamRoom room) {
        ExamRoom existing = getById(id);

        existing.setRoomNumber(room.getRoomNumber());
        existing.setCapacity(room.getCapacity());
        existing.setFloor(room.getFloor());

        return repository.save(existing);
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }
}
