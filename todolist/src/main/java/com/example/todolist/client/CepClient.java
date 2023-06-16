package com.example.todolist.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "cepClient", url = "https://viacep.com.br/ws")
public interface CepClient {

    @GetMapping("/{cep}/json")
    AddressResponse gedAddressInfo(@PathVariable(name = "cep") String cep);
}
