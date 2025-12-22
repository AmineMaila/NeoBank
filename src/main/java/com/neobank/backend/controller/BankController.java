package com.neobank.backend.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.neobank.backend.dto.AccountRequest;
import com.neobank.backend.dto.TransferRequest;
import com.neobank.backend.models.Account;
import com.neobank.backend.services.AccountService;
import com.neobank.backend.services.TransferService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api")
public class BankController {
    private final TransferService transferSvc;
    private final AccountService accSvc;

    public BankController(TransferService transferSvc, AccountService accSvc) {
        this.transferSvc = transferSvc;
        this.accSvc = accSvc;
    }

    @PostMapping("/transfer")
    public ResponseEntity<Void> transfer(@Valid @RequestBody TransferRequest req) {
        transferSvc.transferMoney(req.getFromAccountId(), req.getToAccountId(), req.getAmount());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/account")
    public ResponseEntity<Account> newAccount(@RequestBody AccountRequest req) {
        Account result = accSvc.createAccount(req.getBalance());
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }
}
