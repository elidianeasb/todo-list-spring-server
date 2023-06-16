package com.example.todolist.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "tasks")
@Data

public class TaskEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)

    private Integer id;

    private String text;
    private Boolean completed;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime completedAt;

}
