package com.example.spring_boot.security;


import com.example.spring_boot.entity.User;
import com.example.spring_boot.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    // We need a password encoder only if we do checks here, but typically
    // the check is done automatically by Spring Security after loadUserByUsername.
    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        // The 'role' stored in the DB is expected to be something like "ROLE_ADMIN"
        return org.springframework.security.core.userdetails.User
                .withUsername(user.getUsername())
                .password(user.getPassword())   // already hashed
                .authorities(user.getRole())   // e.g. "ROLE_ADMIN"
                .build();
    }
}