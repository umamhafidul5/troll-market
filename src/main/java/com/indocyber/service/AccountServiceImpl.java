package com.indocyber.service;

import com.indocyber.dto.RegisterAdminDto;
import com.indocyber.dto.RegisterDto;
import com.indocyber.entity.Account;
import com.indocyber.entity.Cart;
import com.indocyber.repository.AccountRepository;
import com.indocyber.repository.CartRepository;
import com.indocyber.security.ApplicationUserDetails;
import com.indocyber.security.MvcSecurityConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class AccountServiceImpl implements AccountService, UserDetailsService {

    private final AccountRepository accountRepository;

    private final CartRepository cartRepository;

    @Autowired
    public AccountServiceImpl(AccountRepository accountRepository, CartRepository cartRepository) {
        this.accountRepository = accountRepository;
        this.cartRepository = cartRepository;
    }

    @Override
    public void registerBuyer(RegisterDto registerDto) {
        PasswordEncoder passwordEncoder = MvcSecurityConfig.passwordEncoder();

        String hashPassword = passwordEncoder.encode(registerDto.getPassword());

        Account account = new Account(registerDto.getUsername(), hashPassword,
                registerDto.getFirstName(), registerDto.getLastName(),
                registerDto.getAddress(), new BigDecimal(0),
                registerDto.getRole());
        accountRepository.save(account);

        Cart cart = new Cart(accountRepository.findById(account.getUsername()).orElseThrow());
        cartRepository.save(cart);
    }

    @Override
    public void registerSeller(RegisterDto registerDto) {
        PasswordEncoder passwordEncoder = MvcSecurityConfig.passwordEncoder();

        String hashPassword = passwordEncoder.encode(registerDto.getPassword());

        Account account = new Account(registerDto.getUsername(), hashPassword,
                registerDto.getFirstName(), registerDto.getLastName(),
                registerDto.getAddress(), new BigDecimal(0), registerDto.getRole());

        accountRepository.save(account);
    }

    @Override
    public void registerAdmin(RegisterAdminDto registerDto) {
        PasswordEncoder passwordEncoder = MvcSecurityConfig.passwordEncoder();

        String hashPassword = passwordEncoder.encode(registerDto.getPassword());

        Account account = new Account(registerDto.getUsername(), hashPassword, null,
                 null, null, null, registerDto.getRole());

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
    public Boolean checkExistingAccount(String username) {

        Long totalUser = accountRepository.count(username);

        return totalUser > 0 ? true : false;

    }
    @Override
    public Account getAccount() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Optional<Account> byId = this.accountRepository.findById(authentication.getName());
        return byId.orElseThrow();
    }

    @Override
    public List<Account> getAccountsByRole(String role) {
        return accountRepository.getAccountsByRole(role);
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
