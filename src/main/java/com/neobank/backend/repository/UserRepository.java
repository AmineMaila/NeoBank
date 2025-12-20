package com.neobank.backend.repository;

import java.util.Optional;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.neobank.backend.models.User;

@Repository
public class UserRepository {
    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<User> userMapper = (rs, rowNum) -> {
        User result = new User(
            rs.getLong("id"),
            rs.getString("username"),
            rs.getString("hashed_password"),
            rs.getString("role")

        );
        return result;
    };

    public UserRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void createUser(User user) {
        final String sql = "INSERT INTO users (username, hashed_password, role) VALUES (?, ?, ?)";

        jdbcTemplate.update(
            sql,
            user.getUsername(),
            user.getHashed_password(),
            user.getRole()
        );
    }

    public Optional<User> findByUsername(String username) {
        final String sql = "SELECT * FROM users WHERE username = ?";

        return jdbcTemplate.queryForStream(sql, userMapper, username).findFirst();
    }

    public Optional<User> findById(Long id) {
        final String sql = "SELECT * FROM users WHERE id = ?";

        return jdbcTemplate.queryForStream(sql, userMapper, id).findFirst();
    }
}
