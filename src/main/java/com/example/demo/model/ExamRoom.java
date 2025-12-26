package com.example.demo.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "exam_rooms")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExamRoom {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    
    @Column(unique = true)
    private String roomNumber;
    
    @Column(name = "room_rows")
    private Integer rows;
    
    @Column(name = "room_columns")
    private Integer columns;
    
    private Integer capacity;

    public void ensureCapacityMatches() {
        if (this.rows != null && this.columns != null) {
            this.capacity = this.rows * this.columns;
        }
    }
}