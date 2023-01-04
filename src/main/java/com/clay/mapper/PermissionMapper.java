package com.clay.mapper;

import com.clay.model.Permission;
import com.clay.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.WARN)
public interface PermissionMapper {

    Permission mapTo(com.clay.entity.Permission permissionEntity);

    com.clay.entity.Permission mapFrom(Permission user);

    default List<Permission> mapAll(Collection<com.clay.entity.Permission> permissionCollection) {
        return permissionCollection.stream().map(this::mapTo).collect(Collectors.toList());
    }

    default List<com.clay.entity.Permission> mapAllFrom(Collection<Permission> permissionCollection) {
        return permissionCollection.stream().map(this::mapFrom).collect(Collectors.toList());
    }
}
