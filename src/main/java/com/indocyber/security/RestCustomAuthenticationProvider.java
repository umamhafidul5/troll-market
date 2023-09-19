package com.indocyber.security;

import com.indocyber.utility.AuthenticationExceptionCustom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

//custom login 3 authentication
@Component
public class RestCustomAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private UserDetailsService userDetailsService;


    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        String name = authentication.getName();
        String pwd = authentication.getCredentials().toString();
        List<String> roles = authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();
        String role = roles.get(0);

        if (
                !name.isBlank() &&
                !pwd.isBlank() &&
                !role.isBlank()
        ) {
            UserDetails userDetails = null;

            try {
                userDetails = userDetailsService.loadUserByUsername(name);

            } catch (Exception e) {

                throw new AuthenticationExceptionCustom("Username doesn't exist!");
            }

            if (userDetails != null) {

                String username = userDetails.getUsername();
                String password = userDetails.getPassword();
                List<String> collect = userDetails.getAuthorities().stream().map(
                                GrantedAuthority::getAuthority)
                        .toList();

                if (username.equals(name)
                        && MvcSecurityConfig.passwordEncoder().matches(pwd, password)
                        && collect.contains(role)) {
                    return new UsernamePasswordAuthenticationToken(
                            userDetails.getUsername(),
                            userDetails.getPassword(),
                            userDetails.getAuthorities()
                    );
                } else if (!username.equals(name)
                        || !MvcSecurityConfig.passwordEncoder().matches(pwd, password)
                        || !collect.contains(role)) {

                    if (!username.equals(name)) {
                        throw new RuntimeException("Username doesn't exist!, please register!");
                    } else if (!MvcSecurityConfig.passwordEncoder().matches(pwd, password)) {
                        throw new RuntimeException("Invalid account!");
                    } else if (!collect.contains(role)) {
                        throw new RuntimeException("Invalid account!");
                    }
                }

                throw new RuntimeException("Something wrong!");

            } else {

                throw new RuntimeException("Something wrong!");
            }
        } else {
            throw new RuntimeException("Please, fill all fields correctly!");
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
