package com.example.todolist.DTO;

import com.example.todolist.entity.TaskEntity;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TaskResponse {
    private Integer identifier;
    private String text;
    private Boolean isCompleted;
    private LocalDateTime startTimestamp;
    private LocalDateTime updatedAt;
    private LocalDateTime completedAt;

    public TaskResponse(TaskEntity taskEntity) {
        this.text = taskEntity.getText();
        this.isCompleted = taskEntity.getCompleted();
        this.startTimestamp = taskEntity.getCreatedAt();
        this.identifier = taskEntity.getId();
        this.updatedAt = taskEntity.getUpdatedAt();
        this.completedAt = taskEntity.getCompletedAt();

    }

}
