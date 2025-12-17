package com.neobank.backend.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class TransferRequest {
    @NotNull(message = "From Account ID is required")
    private Long fromAccountId;

    @NotNull(message = "To Account ID is required")
    private Long toAccountId;

    @NotNull(message = "Amount is required")
    @Min(value = 1, message = "Transfer Amount must be at least 1")
    private BigDecimal amount;

    public Long getFromAccountId() { return fromAccountId; }
    public void setFromAccountId(Long fromAccountId) { this.fromAccountId = fromAccountId; }

    public Long getToAccountId() { return toAccountId; }
    public void setToAccountId(Long toAccountId) { this.toAccountId = toAccountId; }

    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }
}
