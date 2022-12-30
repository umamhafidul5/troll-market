package com.indocyber.service;

import com.indocyber.entity.Account;
import com.indocyber.entity.Cart;
import com.indocyber.entity.CartMerchandise;

import java.util.List;

public interface CartService {

    void createCart(Cart cart);
    List<CartMerchandise> findMerchandiseListOnCartByUsername(String username);

    Cart findByBuyer(Account account);
}
