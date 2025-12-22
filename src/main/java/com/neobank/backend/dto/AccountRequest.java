package com.neobank.backend.dto;

import java.math.BigDecimal;

public class AccountRequest {
    private BigDecimal balance;


    public AccountRequest(BigDecimal balance) {
        this.balance = balance;
    }

    public BigDecimal getBalance() { return balance; }
    public void setBalance(BigDecimal balance) { this.balance = balance; }
}
