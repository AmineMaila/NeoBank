package com.neobank.backend.exception;

public class UsernameNotFoundException extends RuntimeException {
    private final String username;

    public UsernameNotFoundException(String username) {
        super("username \'" + username + "\" was not found");
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}
