package com.leverx.blog.model;

public enum Role {
    USER("user"), ADMIN("admin");

    private final String role;

    Role(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }
}
