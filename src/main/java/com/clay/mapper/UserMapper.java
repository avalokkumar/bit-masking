package com.clay.mapper;

import com.clay.model.User;
import org.mapstruct.*;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.WARN)
public interface UserMapper {

    User mapTo(com.clay.entity.User userEntity);

    com.clay.entity.User mapFrom(User user);


    @AfterMapping
    default void map(User accountDto, @MappingTarget com.clay.entity.User userEntity) {
       //
    }

    @IterableMapping(elementTargetType = User.class)
    List<User> mapTo(List<com.clay.entity.User> roles);

    @IterableMapping(elementTargetType = com.clay.entity.User.class)
    List<com.clay.entity.User> mapFrom(List<User> roles);

   /* default List<User> mapAll(Collection<com.clay.entity.User> userEntities) {
        return userEntities.stream().map(this::mapTo).collect(Collectors.toList());
    }

    default List<com.clay.entity.User> mapAllFrom(Collection<User> userEntities) {
        return userEntities.stream().map(this::mapFrom).collect(Collectors.toList());
    }*/
}