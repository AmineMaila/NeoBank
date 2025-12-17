package com.neobank.backend.exception;

public class AccountNotFoundException extends RuntimeException {
    private final Long accountId;

    public AccountNotFoundException(Long accountId) {
        super("account with id " + accountId + " was not found");
        this.accountId = accountId;
    }

    public Long getAccountId() { return this.accountId; }
}
