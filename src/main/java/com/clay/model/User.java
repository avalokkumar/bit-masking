package com.clay.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;


@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class User {

    private Long id;
    private String name;
    private String email;
    private String phoneNumber;

    private List<Permission> permissions;
}
