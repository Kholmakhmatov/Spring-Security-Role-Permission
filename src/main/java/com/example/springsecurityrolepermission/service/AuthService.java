package com.example.springsecurityrolepermission.service;

import com.example.springsecurityrolepermission.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
@RequiredArgsConstructor
public class AuthService implements UserDetailsService {
    final UserRepository userRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
//        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
//        System.out.println(authorities);// null
//
//        System.out.println(authentication.getCredentials());
//        System.out.println(authentication.getPrincipal());
//        System.out.println(authentication.getDetails());

        return userRepository.findByUserName(username).orElseThrow(() -> new UsernameNotFoundException(username));
    }
}
