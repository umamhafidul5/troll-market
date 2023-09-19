package com.indocyber.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class RestSecurityConfiguration {

    @Autowired
    private JwtRequestFilter jwtRequestFilter;

    @Autowired
    private RestCustomAuthenticationProvider provider;

    @Order(1)
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.antMatcher("/api/**").csrf().disable()
                .authorizeRequests()
                .antMatchers("/api/authenticate").permitAll()
                .antMatchers("/api/registerAccount").permitAll()
                .antMatchers("/api/home/**").hasAnyAuthority("Admin", "Buyer", "Seller")
                .antMatchers("/api/profile/getProfileIndex").hasAnyAuthority("Buyer", "Seller")
                .antMatchers("/api/profile/top-up").hasAuthority("Buyer")
                .antMatchers("/api/shipment/**", "/api/admin/**", "/api/history/**").hasAuthority("Admin")
                .antMatchers("/api/shop/**", "/api/myCart/**").hasAuthority("Buyer")
                .antMatchers("/api/merchandise/**").hasAuthority("Seller")

                .anyRequest().authenticated()
                .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().authenticationProvider(provider)
                .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

        return httpSecurity.build();
    }

    @Bean
    public AuthenticationManager authenticationManager (
            HttpSecurity http,
            PasswordEncoder passwordEncoder,
            UserDetailsService userDetailsService) throws Exception {

        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder)
                .and()
                .authenticationProvider(provider)
                .build();
    }
}
