package com.neobank.backend.security;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.neobank.backend.exception.UsernameNotFoundException;
import com.neobank.backend.models.User;
import com.neobank.backend.repository.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepo;

    public CustomUserDetailsService(UserRepository userRepo) {
        this.userRepo = userRepo;
    }


    @Override
    public SecurityUser loadUserByUsername(String username) {
        User user = userRepo.findByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException(username)); 

        return new SecurityUser(user);
    }
}
