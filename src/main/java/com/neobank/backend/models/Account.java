package com.neobank.backend.models;

import java.math.BigDecimal;

public class Account {
    private Long accountId;
    private Long userId;
    private BigDecimal balance;

    public Account() {}

    public Account(Long accountId, Long userId, BigDecimal balance) {
        this.accountId = accountId;
        this.userId = userId;
        this.balance = balance;
    }

    public Long getAccountId() { return this.accountId; }
    public void setAccountId(Long accountId) { this.accountId = accountId; }

    public Long getUserId() { return this.userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public BigDecimal getBalance() { return this.balance; }
    public void setBalance(BigDecimal balance) { this.balance = balance; }
}
