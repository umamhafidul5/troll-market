package com.indocyber.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
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

        UserDetails userDetails = userDetailsService.loadUserByUsername(name);
        String username = userDetails.getUsername();
        String password = userDetails.getPassword();
        List<String> collect = userDetails.getAuthorities().stream().map(
                        GrantedAuthority::getAuthority)
                .toList();

//        List<String> collect = userDetails.getAuthorities().stream().map(
//              grantedAuthority -> {return grantedAuthority.getAuthority();})
//              .collect(Collectors.toList());

        if (username.equals(name)
                && MvcSecurityConfig.passwordEncoder().matches(pwd, password)
                && collect.contains(role)) {
            return new UsernamePasswordAuthenticationToken(
                    userDetails.getUsername(),
                    userDetails.getPassword(),
                    userDetails.getAuthorities()
            );
        } else {
            System.out.println("Gagal login");
            throw new BadCredentialsException("Gagal login");
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
