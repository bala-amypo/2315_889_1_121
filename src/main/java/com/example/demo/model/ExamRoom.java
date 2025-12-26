package com.example.demo.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "exam_rooms")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExamRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(unique = true)
    private String roomNumber;
    
    private Integer rows;
    private Integer columns;
    private Integer capacity;
    
    public void ensureCapacityMatches() {
        if (rows != null && columns != null) {
            this.capacity = rows * columns;
        }
    }
    
    // Explicit getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getRoomNumber() { return roomNumber; }
    public void setRoomNumber(String roomNumber) { this.roomNumber = roomNumber; }
    public Integer getRows() { return rows; }
    public void setRows(Integer rows) { this.rows = rows; }
    public Integer getColumns() { return columns; }
    public void setColumns(Integer columns) { this.columns = columns; }
    public Integer getCapacity() { return capacity; }
    public void setCapacity(Integer capacity) { this.capacity = capacity; }
    
    public static ExamRoomBuilder builder() {
        return new ExamRoomBuilder();
    }
    
    public static class ExamRoomBuilder {
        private Long id;
        private String roomNumber;
        private Integer rows;
        private Integer columns;
        private Integer capacity;
        
        public ExamRoomBuilder id(Long id) { this.id = id; return this; }
        public ExamRoomBuilder roomNumber(String roomNumber) { this.roomNumber = roomNumber; return this; }
        public ExamRoomBuilder rows(Integer rows) { this.rows = rows; return this; }
        public ExamRoomBuilder columns(Integer columns) { this.columns = columns; return this; }
        public ExamRoomBuilder capacity(Integer capacity) { this.capacity = capacity; return this; }
        
        public ExamRoom build() {
            ExamRoom room = new ExamRoom();
            room.setId(this.id);
            room.setRoomNumber(this.roomNumber);
            room.setRows(this.rows);
            room.setColumns(this.columns);
            room.setCapacity(this.capacity);
            return room;
        }
    }
}