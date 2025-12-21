package com.neobank.backend.services;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import com.neobank.backend.dto.LoginCredentials;
import com.neobank.backend.dto.RegisterRequest;
import com.neobank.backend.exception.UsernameTakenException;
import com.neobank.backend.models.User;
import com.neobank.backend.repository.UserRepository;
import com.neobank.backend.security.JwtUtil;

@Service
public class AuthService {
    private final UserRepository userRepo;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder bcrypt;

    public AuthService(
        UserRepository userRepo,
        JwtUtil jwtUtil,
        PasswordEncoder bcrypt
    ) {
        this.userRepo = userRepo;
        this.jwtUtil = jwtUtil;
        this.bcrypt = bcrypt;
    }

    @Transactional
    public void register(RegisterRequest req) {
        userRepo.findByUsername(req.getUsername())
            .ifPresent(value -> new UsernameTakenException(req.getUsername()));

        User user = new User(
            0L,
            req.getUsername(),
            bcrypt.encode(req.getPassword()),
            "ROLE_USER"
        );
        userRepo.createUser(user);
    }

    public String login(LoginCredentials credentials) {
        User user = userRepo.findByUsername(credentials.getUsername())
            .orElseThrow(() -> new UsernameNotFoundException("authentication failed or is no longer valid"));

        if (!bcrypt.matches(credentials.getPassword(), user.getHashed_password()))
            throw new BadCredentialsException("Wrong password");

        return jwtUtil.generateToken(user.getId());
    }
}
