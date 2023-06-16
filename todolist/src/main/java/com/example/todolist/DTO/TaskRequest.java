package com.example.todolist.DTO;


import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class TaskRequest {

    @NotBlank
    private String text;
    private Boolean completed;
}


