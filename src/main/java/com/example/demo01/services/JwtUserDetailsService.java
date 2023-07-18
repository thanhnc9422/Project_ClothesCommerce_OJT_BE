package com.example.demo01.services;

import java.util.Collections;


import com.example.demo01.models.Customer;
import com.example.demo01.repositories.CustomerRepository;
import com.example.demo01.util.Crypto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class JwtUserDetailsService implements UserDetailsService {
    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    public Crypto crypto;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Customer c = customerRepository.findOneByUsername(username);
        System.out.println(c);
        if (c != null) {
            try {
                return new User(c.getUsername(), new BCryptPasswordEncoder().encode(crypto.decrypt(c.getPassword())),
                        Collections.singleton(new SimpleGrantedAuthority(c.getRole())));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } else {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
    }
}