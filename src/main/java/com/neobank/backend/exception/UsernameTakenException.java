package com.neobank.backend.exception;

public class UsernameTakenException extends RuntimeException {
    private final String username;

    public UsernameTakenException(String username) {
        super("username \"" + username + "\" is taken");
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}
