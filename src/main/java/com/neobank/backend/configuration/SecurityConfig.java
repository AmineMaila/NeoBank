package com.neobank.backend.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.neobank.backend.security.JwtAuthenticationFilter;

@Configuration
public class SecurityConfig {
    private final JwtAuthenticationFilter jwtAuthFilter;

    public SecurityConfig(JwtAuthenticationFilter jwtAuthFilter) {
        this.jwtAuthFilter = jwtAuthFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) {
        http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/auth/**").permitAll()
                .anyRequest().authenticated()
            )
            .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    // This exports the AuthenticationManager bean so we can use it in AuthController
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}

// public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//     http
//         // 1. Disable CSRF (Cross-Site Request Forgery) 
//         // We disable this because we are using stateless JWTs, not browser sessions.
//         .csrf(csrf -> csrf.disable())

//         // 2. Set Route Permissions
//         .authorizeHttpRequests(auth -> auth
//             .requestMatchers("/auth/**").permitAll() // Open for everyone (Login/Register)
//             .anyRequest().authenticated()            // Everything else requires the Token
//         )

//         // 3. Set Session Policy to STATELESS
//         // This tells Spring: "Do not create a HttpSession (cookie). Check the token every time."
//         .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

//         // 4. Add your custom filter BEFORE the standard UsernamePasswordAuthenticationFilter
//         .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

//     return http.build();
// }
