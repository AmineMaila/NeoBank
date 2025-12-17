package com.neobank.backend.exception;

import java.math.BigDecimal;

public class InsufficientFundsException extends RuntimeException {
    private final Long accountId;
    private final BigDecimal balance;
    private final BigDecimal requested;

    public InsufficientFundsException(Long accountId,
                                      BigDecimal balance,
                                      BigDecimal requested) {
        super("Account " + accountId +
              " has insufficient funds (balance=" + balance +
              ", requested=" + requested + ")");
        this.accountId = accountId;
        this.balance = balance;
        this.requested = requested;
    }

    public Long getAccountId() { return accountId; }
    public BigDecimal getBalance() { return balance; }
    public BigDecimal getRequested() { return requested; }
}