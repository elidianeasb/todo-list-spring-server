package com.example.todolist.client;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class AddressResponse {

    @JsonAlias("logradouro")
    private String street;

    @JsonAlias("bairro")
    private String neighborhood;

    @JsonAlias("city")
    private String city;

    @JsonAlias("uf")
    private String state;
}
