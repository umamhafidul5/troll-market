package com.indocyber.service;

import com.indocyber.entity.Cart;
import com.indocyber.entity.CartMerchandise;
import com.indocyber.entity.Merchandise;
import com.indocyber.repository.CartMerchandiseRepository;
import com.indocyber.repository.CartRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final CartMerchandiseRepository cartMerchandiseRepository;

    public CartServiceImpl(CartRepository cartRepository, CartMerchandiseRepository cartMerchandiseRepository) {
        this.cartRepository = cartRepository;
        this.cartMerchandiseRepository = cartMerchandiseRepository;
    }

    @Override
    public void createCart(Cart cart) {
        cartRepository.save(cart);
    }

    @Override
    public List<CartMerchandise> findMerchandiseListOnCartByUsername(String username) {
        return cartMerchandiseRepository.getCartListByUsername(username);
    }
}
