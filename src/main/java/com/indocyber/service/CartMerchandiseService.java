package com.indocyber.service;

import com.indocyber.dto.CartMerchandiseDto;
import com.indocyber.entity.Account;
import com.indocyber.entity.CartMerchandise;

public interface CartMerchandiseService {

    void saveCartMerchandise(CartMerchandiseDto dto, Account account);
}
