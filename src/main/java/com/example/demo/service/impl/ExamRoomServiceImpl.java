package com.example.demo.service; // Ensure the package declaration is correct

import com.example.demo.exception.ApiException;
import com.example.demo.model.ExamRoom;
import com.example.demo.repository.ExamRoomRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExamRoomServiceImpl implements ExamRoomService {

    private final ExamRoomRepository repo;

    public ExamRoomServiceImpl(ExamRoomRepository repo) {
        this.repo = repo;
    }

    @Override
    public ExamRoom add(ExamRoom r) {
        if (r.getRowCount() <= 0 || r.getColumnCount() <= 0) {
            throw new ApiException("Invalid row or column count");
        }

        if (repo.findByRoomNumber(r.getRoomNumber()).isPresent()) {
            throw new ApiException("Exam room already exists");
        }

        // capacity is auto-calculated by @PrePersist/@PreUpdate
        return repo.save(r);
    }

    @Override
    public List<ExamRoom> all() {
        return repo.findAll();
    }
}
