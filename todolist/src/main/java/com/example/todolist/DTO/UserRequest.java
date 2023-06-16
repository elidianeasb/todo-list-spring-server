package com.example.todolist.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserRequest {

    @NotBlank(message = "name is required")
    private String name;
    private String email;
    private String password;

    @NotBlank(message = "Cep cannot be blank")
    @Size(min = 8, max = 8, message = "Cep must have 8 characters")
    private String cep;

    @NotBlank(message = "Number cannot be blank")
    private String number;

    private String complement;
}
