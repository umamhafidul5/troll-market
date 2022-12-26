package com.indocyber.service;

import com.indocyber.dto.RegisterAdminDto;
import com.indocyber.dto.RegisterDto;

public interface AccountService {
    void registerBuyer(RegisterDto registerDto);
    void registerSeller(RegisterDto registerDto);
    void registerAdmin(RegisterAdminDto registerDto);
    String getAccountRole(String username);

    Boolean checkExistingAccount(String username);
}
