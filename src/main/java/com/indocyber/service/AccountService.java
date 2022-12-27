package com.indocyber.service;

import com.indocyber.dto.RegisterAdminDto;
import com.indocyber.dto.RegisterDto;
import com.indocyber.entity.Account;

import java.util.List;

public interface AccountService {
    void registerBuyer(RegisterDto registerDto);
    void saveBuyer(Account account);
    void registerSeller(RegisterDto registerDto);
    void registerAdmin(RegisterAdminDto registerDto);
    String getAccountRole(String username);

    Boolean checkExistingAccount(String username);
    Account getAccount();

    List<Account> getAccountsByRole(String role);
}
