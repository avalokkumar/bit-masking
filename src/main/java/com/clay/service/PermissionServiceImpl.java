package com.clay.service;

import com.clay.exception.PermissionNotFoundException;
import com.clay.mapper.PermissionMapper;
import com.clay.model.Permission;
import com.clay.repo.PermissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PermissionServiceImpl implements PermissionService {

    @Autowired
    private PermissionRepository permissionRepository;

    @Autowired
    private PermissionMapper permissionMapper;

    @Override
    public List<Permission> getAllPermissionsByUserId(Long userId) {
        List<com.clay.entity.Permission> permissionsEntities = permissionRepository.findByUserId(userId);
        return permissionMapper.mapAll(permissionsEntities);
    }

    @Override
    public Permission getPermissionById(Long permissionId) throws PermissionNotFoundException {
        com.clay.entity.Permission permissionEntity = permissionRepository.findById(permissionId)
                .orElseThrow(() -> new PermissionNotFoundException("Permission not found for id " + permissionId));

        return permissionMapper.mapTo(permissionEntity);
    }
}
