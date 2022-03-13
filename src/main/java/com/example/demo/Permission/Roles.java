package com.example.demo.Permission;

public enum Roles {
    STUDENT("STUDENT"),
    ADMIN("ADMIN");

    private final String role;
    Roles(String role) {
        this.role = role;
    }
    public String getRole() {
        return role;
    }
}
