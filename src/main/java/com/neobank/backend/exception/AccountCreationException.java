package com.neobank.backend.exception;

public class AccountCreationException extends RuntimeException {
    public AccountCreationException(String msg) {
        super(msg);
    }
}
