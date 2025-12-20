package com.neobank.backend.repository;

import com.neobank.backend.models.Account;

import java.math.BigDecimal;
import java.util.Optional;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class AccountRepository {
    private final JdbcTemplate jdbcTemplate;
    
    private final RowMapper<Account> accountRowMapper = (rs, rowNum) -> {
        Account account = new Account();
        account.setAccountId(rs.getLong("id"));
        account.setUserId(rs.getLong("user_id"));
        account.setBalance(rs.getBigDecimal("balance"));
        return account;
    };

    public AccountRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Optional<Account> findById(Long id) {
        final String sql = "SELECT * FROM accounts WHERE id = ?";

        return jdbcTemplate.queryForStream(sql, accountRowMapper, id)
            .findFirst();
    }

    public void updateBalance(Long accountId, BigDecimal newBalance) {
        final String sql = "UPDATE accounts SET balance = ? WHERE id = ?";

        jdbcTemplate.update(sql, newBalance, accountId);
    }
}
