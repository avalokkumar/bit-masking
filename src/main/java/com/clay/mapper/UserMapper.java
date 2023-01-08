package com.clay.mapper;

import com.clay.model.User;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.WARN)
public interface UserMapper {

    User mapTo(com.clay.entity.User userEntity);

    @Mapping(target = "permissions", ignore = true)
    com.clay.entity.User mapFrom(User user);

    @IterableMapping(elementTargetType = User.class)
    List<User> mapTo(List<com.clay.entity.User> roles);

    @IterableMapping(elementTargetType = com.clay.entity.User.class)
    List<com.clay.entity.User> mapFrom(List<User> roles);
}