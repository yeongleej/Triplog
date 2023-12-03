package com.example.travel.auth.service;

import com.example.travel.auth.entity.User;
import com.example.travel.auth.entity.UserPrincipal;
import com.example.travel.auth.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.sql.SQLException;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String uniqueId) throws UsernameNotFoundException {
        User user;

        try {
            user = userRepository.findByUniqueId(uniqueId);
        } catch (SQLException | UsernameNotFoundException e) {
            log.info(e.getMessage());
            throw new UsernameNotFoundException("Cannot find User Information");
        }

        log.info("UserPrincipal: " + user.getUnique_id() + " " + user.getEmail());
        return UserPrincipal.create(user);
    }
}