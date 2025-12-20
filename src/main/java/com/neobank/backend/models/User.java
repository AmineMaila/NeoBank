package com.neobank.backend.models;

public class User {
    private Long id;
    private String username;
    private String hashed_password;
    private String role;

    public User() {}

    public User(Long id, String username, String hashed_password, String role) {
        this.id = id;
        this.username = username;
        this.hashed_password = hashed_password;
        this.role = role;
    }

    public String getHashed_password() { return hashed_password; }
    public void setHashed_password(String hashed_password) { this.hashed_password = hashed_password; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
}
