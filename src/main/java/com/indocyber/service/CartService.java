package com.indocyber.service;

import com.indocyber.entity.Account;
import com.indocyber.entity.Cart;
import com.indocyber.entity.CartMerchandise;

import java.math.BigDecimal;
import java.util.List;

public interface CartService {

    void createCart(Cart cart);
    List<CartMerchandise> findMerchandiseListOnCartByUsername(String username);

<<<<<<< HEAD
    Cart findByBuyer(Account account);
=======
    BigDecimal countTotalPriceIncludeShipment();
    void purchasingMerchandiseOnCart();
>>>>>>> d72334a2a91c8a613746e2de16603348b42a34e8
}
