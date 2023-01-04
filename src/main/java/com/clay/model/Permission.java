package com.clay.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class Permission {

    private Long id;
    private String role;
    private List<UserAction> userActions;

}
