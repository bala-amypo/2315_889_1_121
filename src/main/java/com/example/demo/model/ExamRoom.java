package com.example.demo.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "exam_room")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExamRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String roomNumber;

    @Column(name = "row_count")
    private Integer rowCount;

    @Column(name = "column_count")
    private Integer columnCount;

    private Integer capacity;
}
