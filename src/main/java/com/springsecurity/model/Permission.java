package com.springsecurity.model;

public enum  Permission {

    USER("ROLE_USER"), ADMIN("ROLE_ADMIN");

    private String role;

    Permission(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }
}
