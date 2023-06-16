package com.example.todolist.DTO;

import lombok.Data;

@Data
public class UserResponse {

    private String name;
    private String email;


    public UserResponse(String name, String email) {
        this.email = email;
        this.name = name;
    }

}
