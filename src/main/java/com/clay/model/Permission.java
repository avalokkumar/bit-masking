package com.clay.model;

import com.clay.entity.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class Permission {

    private Long id;
    private String role;

    @JsonIgnore
    private User user;
    private List<UserAction> userActions;

}
