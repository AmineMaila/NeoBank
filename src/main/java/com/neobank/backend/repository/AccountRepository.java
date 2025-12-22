package com.neobank.backend.repository;

import com.neobank.backend.models.Account;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
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

    public List<Account> findAllByUserId(Long id) {
        final String sql = "SELECT * FROM accounts WHERE user_id = ?";

        return jdbcTemplate.query(sql, accountRowMapper, id);
    }

    public Optional<Account> findByUserId(Long id, Long user_id) {
        final String sql = "SELECT * FROM accounts WHERE id = ? AND user_id = ?";

        return jdbcTemplate.queryForStream(sql, accountRowMapper, id, user_id).findFirst();
    }

    public void updateBalance(Long accountId, BigDecimal newBalance) {
        final String sql = "UPDATE accounts SET balance = ? WHERE id = ?";

        jdbcTemplate.update(sql, newBalance, accountId);
    }

    public Long create(Account account) {
        final String sql = "INSERT INTO accounts (user_id, balance) VALUES (?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(conn -> {
            PreparedStatement ps = conn.prepareStatement(
                sql,
                Statement.RETURN_GENERATED_KEYS
            );
            ps.setLong(1, account.getUserId());
            ps.setBigDecimal(2, account.getBalance());
            return ps;
        }, keyHolder);

        return keyHolder.getKey().longValue();
    }
}
