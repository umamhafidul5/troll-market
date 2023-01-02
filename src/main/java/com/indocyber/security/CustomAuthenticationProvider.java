package com.indocyber.security;

import com.indocyber.utility.AuthenticationExceptionCustom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

//custom login 3 authentication
@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private HttpServletRequest httpServletRequest;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        String name = authentication.getName();
        String pwd = authentication.getCredentials().toString();
        String role = httpServletRequest.getParameter("role");

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
                        throw new AuthenticationExceptionCustom("Username doesn't exist!, please register!");
                    } else if (!MvcSecurityConfig.passwordEncoder().matches(pwd, password)) {
                        throw new AuthenticationExceptionCustom("Invalid account!");
                    } else if (!collect.contains(role)) {
                        throw new AuthenticationExceptionCustom("Invalid account!");
                    }
                }

                throw new AuthenticationExceptionCustom("Something wrong!");

            } else {

                throw new AuthenticationExceptionCustom("Something wrong!");
            }
        } else {
            throw new AuthenticationExceptionCustom("Please, fill all fields correctly!");
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
