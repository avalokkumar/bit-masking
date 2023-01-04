package com.clay.service;

import com.clay.exception.UserAlreadyExistsException;
import com.clay.exception.UserNotFoundException;
import com.clay.mapper.UserMapper;
import com.clay.model.User;
import com.clay.repo.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    @Override
    public List<User> listAllUsers() {
        return userMapper.mapTo(userRepository.findAll());
    }

    @Override
    public User createUser(User userDetail) throws UserAlreadyExistsException {
        if (userRepository.existsByEmail(userDetail.getEmail())) {
            throw new UserAlreadyExistsException("User is already created");
        }
        return userMapper.mapTo(userRepository.save(userMapper.mapFrom(userDetail)));
    }

    @Override
    public User updateUser(User userDetail) throws UserNotFoundException {
        if (!userRepository.existsById(userDetail.getId())) {
            throw new UserNotFoundException("User not found for id " + userDetail.getId());
        }
        return userMapper.mapTo(userRepository.save(userMapper.mapFrom(userDetail)));
    }

    @Override
    public void deleteUser(Long userId) throws UserNotFoundException {
        if (!userRepository.existsById(userId)) {
            throw new UserNotFoundException("User not found for id " + userId);
        }

        userRepository.deleteById(userId);
    }

    @Override
    public User getUserById(Long userId) throws UserNotFoundException {
        com.clay.entity.User userEntity = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User not found for id " + userId));
        return userMapper.mapTo(userEntity);

    }
}
