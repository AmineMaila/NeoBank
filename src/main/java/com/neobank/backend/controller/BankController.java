package com.neobank.backend.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.neobank.backend.dto.TransferRequest;
import com.neobank.backend.services.TransferService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api")
public class BankController {
    private final TransferService svc;

    public BankController(TransferService svc) {
        this.svc = svc;
    }

    @PostMapping("/transfer")
    public ResponseEntity<Void> transfer(@Valid @RequestBody TransferRequest req) {
        svc.transferMoney(req.getFromAccountId(), req.getToAccountId(), req.getAmount());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
