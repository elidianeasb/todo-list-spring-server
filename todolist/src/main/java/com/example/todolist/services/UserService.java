package com.example.todolist.services;

import com.example.todolist.DTO.UserRequest;
import com.example.todolist.DTO.UserResponse;
import com.example.todolist.client.AddressResponse;
import com.example.todolist.client.CepClient;
import com.example.todolist.entity.UserEntity;
import com.example.todolist.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    @Autowired //Injeta
    private UserRepository userRepository;

    @Autowired
    private CepClient cepClient;

    public ResponseEntity<?> createUser(UserRequest newUser) {
        UserEntity possibleUser = userRepository.findByEmail(newUser.getEmail());

        AddressResponse address = getAddressFromCep(newUser.getCep());


        if (possibleUser != null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("email already exist");

        } else {
            UserEntity user = new UserEntity();
            user.setEmail((newUser.getEmail()));
            user.setName((newUser.getName()));
            user.setPassword((newUser.getPassword()));
            user.setStreet(address.getStreet());
            user.setNeighborhood(address.getNeighborhood());
            user.setCity(address.getCity());
            user.setState(address.getState());

            user.setCep(newUser.getCep());
            user.setNumber(newUser.getNumber());
            user.setComplement(newUser.getComplement());

            UserEntity createdUser = userRepository.save(user);

            UserResponse response = new UserResponse(createdUser.getEmail(), createdUser.getName());

            return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
        }

    }
    public List<UserResponse> listUsers() {
        List<UserEntity> userEntityList = userRepository.findAll();

        List<UserResponse> userResponseList = new ArrayList<>();

        userEntityList.forEach(element -> {
            userResponseList.add(new UserResponse(element.getName(), element.getEmail()));
        });

        return userResponseList;
    }

    private AddressResponse getAddressFromCep(String cep){
        return cepClient.gedAddressInfo(cep);
    }

}
