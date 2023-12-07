package com.example.simple;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.model.User;

@RestController
public class SimpleController {

    private List<User> users = new ArrayList<>();

    @GetMapping(value = "users")
    public ResponseEntity<List<User>> getAllUserController() {

        if (!users.isEmpty()) {
                    // users.sort(Comparator.comparing(User::getId));
            return ResponseEntity
                    .ok()
                    .body(users);
        } else {
            return ResponseEntity
                    .noContent()
                    .build();
        }

    }

    @PostMapping(value = "user")
    public ResponseEntity<String> addUserController(@RequestBody User user) {
        boolean isUserExist = users.stream().anyMatch(existedUser -> existedUser.getId().equals(user.getId()));
        if (isUserExist) {

            return ResponseEntity
                    .badRequest()
                    .body("user already exists");
        } else {
            users.add(user);
        }
        return ResponseEntity

                .ok()
                .body("addUserSuccess");

    }

    @PutMapping(value = "user")
    public ResponseEntity<String> updateUserController(@RequestBody User user) {
        if (null == user.getId()) {
            return ResponseEntity
                    .badRequest()
                    .body("Can't updaete : filed 'id' is missing");
        }

        User quryUser = users
                .stream()
                .filter(existedUser -> user.getId().equals(existedUser.getId()))
                .findAny().orElse(null);
        if (null == quryUser) {
            return ResponseEntity
                    .badRequest()
                    .body("Can't update: User does't exist");

        }
        if (null != user.getName()) {
            quryUser.setName(user.getName());
        }
        if (null != user.getSurname()) {
            quryUser.setSurname(user.getSurname());
        }
        // users.stream().filter(existedUser ->
        // existedUser.getId().equals(user.getId()));

        // Solution #1
        deleteUser(user);
        users.add(quryUser);
        return ResponseEntity
                .ok()
                .body("Update User Success.");
    }

    @DeleteMapping(value = "user/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable(name = "id") Long id) {
        if (areUserExist(id)) {
            users.removeIf(user -> user.getId().equals(id));
            return ResponseEntity
                    .ok()
                    .body("delete successfully");
        } else {
            return ResponseEntity
                    .badRequest()
                    .body("user not exist");
        }

    }

    private boolean areUserExist(Long id) {
        return users
                .stream()
                .anyMatch(existedUser -> id.equals(existedUser.getId()));
    }

    private void deleteUser(User user) {
        users.removeIf(existedUser -> user.getId().equals(existedUser.getId()));
    }

    // private void addUser(User user) {
    //     users.add(user);
    // }
}

// consume = incoming
// produces = outgoing


