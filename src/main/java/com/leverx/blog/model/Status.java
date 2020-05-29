package com.leverx.blog.model;

public enum Status {
    PUBLIC("public"), DRAFT("draft");

    private final String status;

    Status(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

}
