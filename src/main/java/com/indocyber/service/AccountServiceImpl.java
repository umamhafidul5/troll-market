package com.indocyber.service;

import com.indocyber.dto.RegisterDto;
import com.indocyber.entity.Account;
import com.indocyber.repository.AccountRepository;
import com.indocyber.security.ApplicationUserDetails;
import com.indocyber.security.MvcSecurityConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional
public class AccountServiceImpl implements AccountService, UserDetailsService {

    private final AccountRepository accountRepository;

    @Autowired
    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public void registerBuyer(RegisterDto registerDto) {
        PasswordEncoder passwordEncoder = MvcSecurityConfig.passwordEncoder();

        String hashPassword = passwordEncoder.encode(registerDto.getPassword());

        Account account = new Account(registerDto.getUsername(), hashPassword,
                registerDto.getFirstName(), registerDto.getLastName(),
                registerDto.getAddress(), null, registerDto.getRole());

        accountRepository.save(account);
    }

    @Override
    public void registerSeller(RegisterDto registerDto) {
        PasswordEncoder passwordEncoder = MvcSecurityConfig.passwordEncoder();

        String hashPassword = passwordEncoder.encode(registerDto.getPassword());

        Account account = new Account(registerDto.getUsername(), hashPassword,
                registerDto.getFirstName(), registerDto.getLastName(),
                registerDto.getAddress(), null, registerDto.getRole());

        accountRepository.save(account);
    }

    @Override
    public void registerAdmin(RegisterDto registerDto) {
        PasswordEncoder passwordEncoder = MvcSecurityConfig.passwordEncoder();

        String hashPassword = passwordEncoder.encode(registerDto.getPassword());

        Account account = new Account(registerDto.getUsername(), hashPassword,
                registerDto.getFirstName(), registerDto.getLastName(),
                registerDto.getAddress(), null, registerDto.getRole());

        accountRepository.save(account);
    }

    @Override
    public String getAccountRole(String username) {

        Optional<Account> accountOptional = accountRepository.findById(username);

        Account tempAccount = null;
        if (accountOptional.isPresent()) {
            tempAccount = accountOptional.get();
        }

        return tempAccount.getRole();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        System.out.println("username: " + username);

        Optional<Account> optionalAccount = accountRepository.findById(username);

        Account tempAccount = null;
        if (optionalAccount.isPresent()) {
            tempAccount = optionalAccount.get();
        }

        return new ApplicationUserDetails(tempAccount);
    }
}
