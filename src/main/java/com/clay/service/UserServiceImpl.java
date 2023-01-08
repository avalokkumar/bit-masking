package com.clay.service;

import com.clay.entity.Permission;
import com.clay.exception.UserAlreadyExistsException;
import com.clay.exception.UserNotFoundException;
import com.clay.mapper.PermissionMapper;
import com.clay.mapper.UserMapper;
import com.clay.mapper.UserMapperPrePostProcessor;
import com.clay.model.User;
import com.clay.repo.PermissionRepository;
import com.clay.repo.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.clay.util.Constant.SET;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PermissionRepository permissionRepository;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserMapperPrePostProcessor userMapperPrePostProcessor;

    @Autowired
    private PermissionMapper permissionMapper;

    @Override
    public List<User> listAllUsers() {
        return userMapper.mapTo(userRepository.findAll());
    }

    @Override
    public User createUser(User userDetail) throws UserAlreadyExistsException {
        if (userRepository.existsByEmail(userDetail.getEmail())) {
            throw new UserAlreadyExistsException("User is already created");
        }
        com.clay.entity.User userFromDb = userRepository.save(userMapper.mapFrom(userDetail));
        List<Permission> permissionsEntities = permissionMapper.mapFrom(userDetail.getPermissions());
        permissionsEntities.forEach(permission -> permission.setUser(userFromDb));
        permissionRepository.saveAll(permissionsEntities);
        userFromDb.setPermissions(permissionsEntities);
        userMapperPrePostProcessor.mapToUserEntity(SET, userFromDb);
        return userMapper.mapTo(userFromDb);
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
