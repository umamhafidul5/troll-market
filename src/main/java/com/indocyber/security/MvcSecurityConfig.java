package com.indocyber.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

//configuration security
@Configuration
@EnableWebSecurity
public class MvcSecurityConfig {

    @Order(2)
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception{
        httpSecurity.authorizeRequests()
                .antMatchers("/style.css", "/account/**").permitAll()
//                .antMatchers("/account/**", "/api/**").permitAll()
                .antMatchers("/home/**").hasAnyAuthority("Admin", "Buyer", "Seller")
                .antMatchers("/profile/**").hasAnyAuthority("Buyer", "Seller")
                .antMatchers("/profile/top-up").hasAuthority("Buyer")
                .antMatchers("/shipment/**", "/admin/**", "/history/**").hasAuthority("Admin")
                .antMatchers("/shop/**", "/myCart/**").hasAuthority("Buyer")
                .antMatchers("/merchandise/**").hasAuthority("Seller")
//                .antMatchers("/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin().loginPage("/my-login/showMyLoginPage")
                .loginProcessingUrl("/authenticateTheUser").permitAll()
//                .successHandler(loginHandlerRedirect) // multiple landing
                .and()
                .logout().permitAll()
                .and()
                .exceptionHandling().accessDeniedPage("/account/accessDenied");
        return httpSecurity.build();
    };

    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(10);
    }
}
