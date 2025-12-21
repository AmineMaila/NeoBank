package com.neobank.backend.security;

import java.util.List;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.neobank.backend.models.Account;
import com.neobank.backend.repository.AccountRepository;

@Component
public class AccountSecurity {
    private final AccountRepository accountRepo;

    public AccountSecurity(AccountRepository accountRepo) {
        this.accountRepo = accountRepo;
    }

    public boolean isAuthorized(Long accountId) {
        Long clientId = Long.valueOf(((SecurityUser)SecurityContextHolder
            .getContext()
            .getAuthentication()
            .getPrincipal()).getId());
        
        List<Account> clientAccounts = this.accountRepo.findByUserId(clientId); 

        for (var acc : clientAccounts)
            if (acc.getAccountId() == accountId)
                return true;
        throw new AccessDeniedException("You do not own account " + accountId);
    }
}
