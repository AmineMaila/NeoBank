package com.neobank.backend.models;

public class User {
    private Long user_id;
    private String username;
    private String hashed_password;
    private String role;

    public User() {}

    public User(Long user_id, String username, String hashed_password, String role) {
        this.user_id = user_id;
        this.username = username;
        this.hashed_password = hashed_password;
        this.role = role;
    }

    public String getHashed_password() { return hashed_password; }
    public void setHashed_password(String hashed_password) { this.hashed_password = hashed_password; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public Long getUser_id() { return user_id; }
    public void setUser_id(Long user_id) { this.user_id = user_id; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
}
