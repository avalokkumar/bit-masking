package com.clay.rest;

import com.clay.exception.UserAlreadyExistsException;
import com.clay.exception.UserNotFoundException;
import com.clay.model.User;
import com.clay.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/user", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/hello")
    public ResponseEntity<String> sayHello() {
        return ResponseEntity.ok("Hello Clay");
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userService.listAllUsers());
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) throws UserAlreadyExistsException {
        return ResponseEntity.ok(userService.createUser(user));
    }

    @PutMapping
    public ResponseEntity<User> updateUser(@RequestBody User user) throws UserNotFoundException {
        return ResponseEntity.ok(userService.updateUser(user));
    }

    @DeleteMapping(value = "/{user_id}")
    public ResponseEntity<User> deleteUser(@PathVariable("user_id") String userId) throws UserNotFoundException {
        userService.deleteUser(Long.valueOf(userId));
        return ResponseEntity.noContent().build();
    }
}
