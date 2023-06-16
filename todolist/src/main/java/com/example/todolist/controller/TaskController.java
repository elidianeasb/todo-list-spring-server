package com.example.todolist.controller;

import com.example.todolist.DTO.TaskRequest;
import com.example.todolist.DTO.TaskResponse;
import com.example.todolist.entity.TaskEntity;
import com.example.todolist.entity.UserEntity;
import com.example.todolist.repository.TaskRepository;
import com.example.todolist.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
public class TaskController {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    public TaskController(TaskRepository taskRepository, UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/tasks")
    ResponseEntity<String> createTask(
            @RequestBody @Valid TaskRequest taskRequest,

            @RequestParam(name = "email") String email

    ) {
        UserEntity user = userRepository.findByEmail(email);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Email not found");
        } else {
            TaskEntity newTask = new TaskEntity();
            newTask.setText(taskRequest.getText());
            newTask.setCompleted(false);
            newTask.setCreatedAt(LocalDateTime.now());

            TaskEntity createdTask = taskRepository.save(newTask);
            user.getTasks().add(createdTask);
            userRepository.save(user);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }
    }


    @GetMapping("/tasks/{taskId}")
    ResponseEntity<TaskEntity> getTaskById(@PathVariable(value = "taskId") Integer taskId) {
        Optional<TaskEntity> taskOptional = taskRepository.findById(taskId);
        if (taskOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(taskOptional.get());
        }
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/tasks")
    ResponseEntity<?> getTasksByEmail(@RequestParam(name = "email", required = false) String email) {
        if (email == null) {

            List<TaskResponse> taskResponseList = new ArrayList<>();
            List<TaskEntity> taskEntityList = taskRepository.findAll();

            taskEntityList.forEach(task -> {
                taskResponseList.add(new TaskResponse(task));
            });
            return ResponseEntity.status(HttpStatus.OK).body(taskResponseList);
        } else {
            UserEntity user = userRepository.findByEmail(email);

            if (user == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Email not found");
            } else {
                List<TaskEntity> taskEntityList = user.getTasks();
                List<TaskResponse> taskResponseList = new ArrayList<>();

                taskEntityList.forEach(task -> {
                    taskResponseList.add(new TaskResponse(task));
                });
                return ResponseEntity.status(HttpStatus.OK).body(taskResponseList);
            }
        }
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @DeleteMapping("/tasks/{taskId}")
    ResponseEntity<?> deleteTaskById(
            @PathVariable(value = "taskId") Integer taskId,
            @RequestParam(name = "email") String email
    ){

        Optional<TaskEntity> taskOptional = taskRepository.findById(taskId);
        UserEntity user = userRepository.findByEmail(email);

        if (user == null) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Email not found");

        if (taskOptional.isPresent()) {
            TaskEntity existingTask = taskOptional.get();
            user.getTasks().remove(existingTask);
            userRepository.save(user);
            taskRepository.deleteById(taskId);
            return ResponseEntity.status(HttpStatus.OK).build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PutMapping("/tasks/{taskId}")
    ResponseEntity<?> updateTaskById(
            @PathVariable(value = "taskId") Integer taskId,
            @RequestBody TaskEntity updatedTask
    ) {
        Optional<TaskEntity> taskOptional = taskRepository.findById(taskId);
        if (taskOptional.isPresent()) {
            TaskEntity existingTask = taskOptional.get();

            if (updatedTask.getText() != null) existingTask.setText(updatedTask.getText());
            if (updatedTask.getCompleted() != null) existingTask.setCompleted(updatedTask.getCompleted());
            if (updatedTask.getCompleted() == true) existingTask.setCompletedAt(LocalDateTime.now());
            existingTask.setUpdatedAt(LocalDateTime.now());

            TaskEntity savedTask = taskRepository.save(existingTask);

            return ResponseEntity.status(HttpStatus.OK).body(savedTask);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

        }
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/tasks/{taskId}/complete")
    ResponseEntity<String> completeTask(
            @PathVariable(value = "taskId") Integer taskId
    ){
        Optional<TaskEntity> taskOptional = taskRepository.findById(taskId);
        if (taskOptional.isPresent()) {
            TaskEntity existingTask = taskOptional.get();
            existingTask.setCompleted(!existingTask.getCompleted());

            if (existingTask.getCompleted()) {
                existingTask.setCompletedAt(LocalDateTime.now());
            } else {
                existingTask.setCompletedAt(null);
            }

            TaskEntity savedTask = taskRepository.save(existingTask);
            return ResponseEntity.status(HttpStatus.OK).build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}

