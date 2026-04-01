package com.example.food_store.service.impl;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

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
        log.info("CustomUserDetailsService: User found: {}, Roles: {}, Password hash length: {}",
                user.getEmail(), user.getRoleNamesDisplay(),
                user.getPassword() != null ? user.getPassword().length() : 0);
        List<SimpleGrantedAuthority> authorities = user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.getName()))
                .collect(Collectors.toList());
        return new User(
                user.getEmail(),
                user.getPassword(),
                authorities.isEmpty() ? Collections.emptyList() : authorities);

    }

}
