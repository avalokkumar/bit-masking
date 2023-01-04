package com.clay.model;

public enum UserAction {
    UPDATE_LOGIN_CONFIG("UpdateLoginConfig"),
    UPDATE_SUBSCRIPTION("UpdateSubscription"),
    ADD_USERS("AddUsers"),
    DELETE_USERS("DeleteUsers"),
    CREATE_USER("CreateUser"),
    LIST_USERS("ListUsers"),
    UPDATE_USER_DETAIL("UpdateUserDetail");

    private String actionName;

    UserAction(String actionName) {
        this.actionName = actionName;
    }

    public String getActionName() {
        return this.actionName;
    }
}
