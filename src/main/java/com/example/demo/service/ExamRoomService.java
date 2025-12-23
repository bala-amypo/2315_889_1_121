package com.example.demo.service;

import com.example.demo.model.ExamRoom;

import java.util.List;

public interface ExamRoomService {

    ExamRoom add(ExamRoom room);

    List<ExamRoom> all();

    ExamRoom getById(Long id);

    ExamRoom update(Long id, ExamRoom room);

    void delete(Long id);
}
