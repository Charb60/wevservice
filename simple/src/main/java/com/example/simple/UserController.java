package com.example.simple;

import java.util.ArrayList;

import com.example.model.User;
import com.example.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;


@RestController
@RequestMapping("/api/v1/")
public class UserController {

    @Autowired
    UserRepository userRepository;

    @GetMapping(value = "users")
    public ResponseEntity<ArrayList<User>> getAllUserController() {
        return ResponseEntity
        .ok()
        .body(userRepository.queryAllUser());
    }
}
