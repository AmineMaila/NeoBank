package com.neobank.backend.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(InsufficientFundsException.class)
	public ResponseEntity<Map<String, Object>> insufficientFunds(InsufficientFundsException ex) {
		return ResponseEntity.status(HttpStatus.CONFLICT)
			.body(Map.of(
				"status", 409,
				"error", "Conflict",
				"message", ex.getMessage(),
				"currentBalance", ex.getBalance(),
				"rejectedAmmount", ex.getRequested(),
				"accountId", ex.getAccountId()
			));
	}

	@ExceptionHandler(AccountNotFoundException.class)
	public ResponseEntity<Map<String, Object>> accountNotFound(AccountNotFoundException ex) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND)
			.body(Map.of(
				"status", 404,
				"error", "Not Found",
				"message", ex.getMessage(),
				"accountId", ex.getAccountId()
			));
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String, Object>> validation(MethodArgumentNotValidException ex) {
		Map<String, String> errors = new HashMap<>();
		
		ex.getBindingResult().getFieldErrors().forEach(error -> {
			errors.put(error.getField(), error.getDefaultMessage());
		});

		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
			.body(Map.of(
				"status", 400,
				"error", "Bad Request",
				"details", errors
			));
	}
}
