package com.example.food_store.service.impl;

import java.util.Collections;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomUserDetailsService implements UserDetailsService {
    private final UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("=== CustomUserDetailsService: Attempting to load user with email: {} ===", username);
        com.example.food_store.domain.User user = this.userService.getUserByEmail(username);
        if (user == null) {
            log.warn("CustomUserDetailsService: User not found for email: {}", username);
            throw new UsernameNotFoundException("user not found");
        }
        log.info("CustomUserDetailsService: User found: {}, Role: {}, Password hash length: {}",
                user.getEmail(), user.getRole().getName(),
                user.getPassword() != null ? user.getPassword().length() : 0);
        return new User(
                user.getEmail(),
                user.getPassword(),
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + user.getRole().getName())));

    }

}
