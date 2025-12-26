package com.example.demo.service;

import java.util.List;

import com.example.demo.model.ExamRoom;

public interface ExamRoomService{
    ExamRoom addRoom(ExamRoom room);
    List<ExamRoom> getAllRooms();
}