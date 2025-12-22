package com.neobank.backend.services;

import java.math.BigDecimal;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.neobank.backend.exception.AccountCreationException;
import com.neobank.backend.models.Account;
import com.neobank.backend.repository.AccountRepository;
import com.neobank.backend.security.SecurityUser;

@Service
public class AccountService {
    private final AccountRepository accountRepo;

    public AccountService(AccountRepository accountRepo) {
        this.accountRepo = accountRepo;
    }

    @Transactional
    public Account createAccount(BigDecimal balance) {
        Account newAccount = new Account(
            0L,
            ((SecurityUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId(),
            balance
        );
        
        Long id = accountRepo.create(newAccount);
        Account result = accountRepo.findById(id)
            .orElseThrow(() -> new AccountCreationException("Something went wrong while creating your account"));
        return result;
    }
}