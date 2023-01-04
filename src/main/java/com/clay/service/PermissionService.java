package com.clay.service;

import com.clay.exception.PermissionNotFoundException;
import com.clay.model.Permission;

import java.util.List;

public interface PermissionService {

    List<Permission> getAllPermissionsByUserId(Long userId);

    Permission getPermissionById(Long permissionId) throws PermissionNotFoundException;
}
