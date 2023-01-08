package com.clay.mapper;

import com.clay.model.Permission;
import com.clay.model.User;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.WARN)
public interface PermissionMapper {

    Permission mapTo(com.clay.entity.Permission permissionEntity);

    com.clay.entity.Permission mapFrom(Permission permission);

    @IterableMapping(elementTargetType = Permission.class)
    List<Permission> mapTo(List<com.clay.entity.Permission> roles);

    @IterableMapping(elementTargetType = com.clay.entity.Permission.class)
    List<com.clay.entity.Permission> mapFrom(List<Permission> roles);

}
