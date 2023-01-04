package com.indocyber.service;

import com.indocyber.dto.CartMerchandiseDto;
import com.indocyber.entity.Account;
import com.indocyber.entity.CartMerchandise;

import java.util.List;

public interface CartMerchandiseService {

    void saveCartMerchandise(CartMerchandiseDto dto, Account account);

    List<CartMerchandise> getCartListByUsername(String username);

    void deleteCartMerchandise(int id);
}
