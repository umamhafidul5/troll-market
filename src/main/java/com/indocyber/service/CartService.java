package com.indocyber.service;

import com.indocyber.entity.Cart;
import com.indocyber.entity.CartMerchandise;
import com.indocyber.entity.Merchandise;

import java.math.BigDecimal;
import java.util.List;

public interface CartService {

    void createCart(Cart cart);
    List<CartMerchandise> findMerchandiseListOnCartByUsername(String username);

    BigDecimal countTotalPriceIncludeShipment();
    void purchasingMerchandiseOnCart();
}
