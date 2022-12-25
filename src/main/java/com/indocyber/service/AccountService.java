package com.indocyber.service;

import com.indocyber.dto.RegisterDto;

public interface AccountService {
    void registerBuyer(RegisterDto registerDto);
    void registerSeller(RegisterDto registerDto);
    void registerAdmin(RegisterDto registerDto);
    String getAccountRole(String username);
}
