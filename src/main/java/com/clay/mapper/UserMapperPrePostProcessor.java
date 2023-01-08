package com.clay.mapper;

import com.clay.exception.PermissionNotFoundException;
import com.clay.model.Permission;
import com.clay.model.User;
import com.clay.model.UserAction;
import com.clay.repo.UserActionRepository;
import com.clay.util.BitConverter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Component
public class UserMapperPrePostProcessor {

    @Autowired
    private BitConverter bitConverter;

    @Autowired
    private UserActionRepository userActionRepository;

    public void mapToUserEntity(String operation, com.clay.entity.User userEntity) {

        for (com.clay.entity.Permission permission : userEntity.getPermissions()) {
            try {
                List<String> userActionNames = userActionRepository.findActionNameByPermissionId(permission.getId());
                BigInteger userActionBitmask = bitConverter.buildUserActionBitmask(operation, permission.getId(), userActionNames);
                permission.setUserActionsBitmask(userActionBitmask);
//                permissionMapping.get(permission.getId()).setUserActionsBitmask(userActionBitmask);
            } catch (PermissionNotFoundException e) {
                log.error("label=UserMapperPostProcessor.map : Permission Not Found {}", e.getMessage());
            }
        }
    }

    public void mapToUser(com.clay.entity.User userEntity, User user) {

        Map<Long, com.clay.entity.Permission> permissionMapping = userEntity.getPermissions()
                .stream()
                .collect(Collectors.toMap(com.clay.entity.Permission::getId, permissionEntity -> permissionEntity));

        for (Permission permission : user.getPermissions()) {
            com.clay.entity.Permission permissionEntity = permissionMapping.get(permission.getId());

            List<String> userActions = bitConverter.buildUserActions(permission.getId(), permissionEntity.getUserActionsBitmask());
            List<UserAction> userActionsValues = userActions.stream().map(UserAction::valueOf).collect(Collectors.toList());

            permission.setUserActions(userActionsValues);
        }
    }
}
