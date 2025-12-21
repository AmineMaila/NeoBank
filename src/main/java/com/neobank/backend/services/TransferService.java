package com.neobank.backend.services;

import java.math.BigDecimal;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.neobank.backend.exception.AccountNotFoundException;
import com.neobank.backend.exception.InsufficientFundsException;
import com.neobank.backend.models.Account;
import com.neobank.backend.repository.AccountRepository;

@Service
public class TransferService {
    private final AccountRepository accountRepo;

    public TransferService (AccountRepository accountRepo) {
        this.accountRepo = accountRepo;
    }

    @PreAuthorize("@accountSecurity.isAuthorized(#p0)")
    @Transactional
    public void transferMoney(Long fromAccountId, Long toAccountId, BigDecimal ammount) {
        Account fromAccount = accountRepo.findById(fromAccountId).orElseThrow(
            () -> new AccountNotFoundException(fromAccountId)
        );

        Account toAccount = accountRepo.findById(toAccountId).orElseThrow(
            () -> new AccountNotFoundException(toAccountId)
        );

        BigDecimal fromBalance = fromAccount.getBalance();

        if (fromBalance.compareTo(ammount) < 0) {
            throw new InsufficientFundsException(fromAccountId, fromBalance, ammount);
        }

        BigDecimal newBalanceFrom = fromBalance.subtract(ammount);
        BigDecimal newBalanceTo = toAccount.getBalance().add(ammount);

        accountRepo.updateBalance(fromAccountId, newBalanceFrom);
        accountRepo.updateBalance(toAccountId, newBalanceTo);
    }
}
