package com.example.todolist.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "users")
@Data

public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String name;

    @Column(unique = true)
    private String email;

    private String password;

    @OneToMany
    private List<TaskEntity> tasks = new ArrayList<>(); //cria uma lista vazia e não nula

    private String cep;

    private String number;

    private String complement;

    private String street;

    private String neighborhood;

    private String city;

    private  String state;


}
