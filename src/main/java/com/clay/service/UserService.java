package com.clay.service;

import com.clay.exception.UserAlreadyExistsException;
import com.clay.exception.UserNotFoundException;
import com.clay.model.User;

import java.util.List;

public interface UserService {

    List<User> listAllUsers();

    User createUser(User userDetail) throws UserAlreadyExistsException;

    User updateUser(User userDetail) throws UserNotFoundException;

    void deleteUser(Long userId) throws UserNotFoundException;

    User getUserById(Long userId) throws UserNotFoundException;
}
