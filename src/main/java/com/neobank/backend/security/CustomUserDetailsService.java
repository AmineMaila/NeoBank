package com.neobank.backend.security;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.neobank.backend.models.User;
import com.neobank.backend.repository.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepo;

    public CustomUserDetailsService(UserRepository userRepo) {
        this.userRepo = userRepo;
    }


    @Override
    public SecurityUser loadUserByUsername(String id) {
        User user = userRepo.findById(Long.valueOf(id))
            .orElseThrow(() -> new UsernameNotFoundException("authentication failed or is no longer valid"));

        return new SecurityUser(user);
    }
}
