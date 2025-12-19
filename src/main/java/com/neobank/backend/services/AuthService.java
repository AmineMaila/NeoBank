package com.neobank.backend.services;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
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
    private final UserDetailsService userDetailsService;

    public AuthService(
        UserRepository userRepo,
        JwtUtil jwtUtil,
        PasswordEncoder bcrypt,
        UserDetailsService userDetailsService    
    ) {
        this.userRepo = userRepo;
        this.jwtUtil = jwtUtil;
        this.bcrypt = bcrypt;
        this.userDetailsService = userDetailsService;
    }

    @Transactional
    public void register(RegisterRequest req) {
        Integer count = userRepo.usersCount(req.getUsername());
        if (count != null && count > 0)
            throw new UsernameTakenException(req.getUsername());

        User user = new User(
            0L,
            req.getUsername(),
            bcrypt.encode(req.getPassword()),
            "ROLE_USER"
        );
        userRepo.createUser(user);
    }

    public String login(LoginCredentials credentials) {
        UserDetails user = userDetailsService.loadUserByUsername(credentials.getUsername());

        if (!bcrypt.matches(credentials.getPassword(), user.getPassword()))
            throw new BadCredentialsException("Wrong password");
        
        return jwtUtil.generateToken(credentials.getUsername());
    }
}
