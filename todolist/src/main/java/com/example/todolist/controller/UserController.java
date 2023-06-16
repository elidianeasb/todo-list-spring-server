package com.example.todolist.controller;

import com.example.todolist.DTO.UserRequest;
import com.example.todolist.DTO.UserResponse;
import com.example.todolist.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserService service;

    @PostMapping("/user")
    ResponseEntity<?> createUser(@RequestBody @Valid UserRequest newUser){

        return service.createUser(newUser);
    }

    @GetMapping("/users")
    List<UserResponse> listUsers(){
        return service.listUsers();
    }
}

