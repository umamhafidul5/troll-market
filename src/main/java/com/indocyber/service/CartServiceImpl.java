package com.indocyber.service;

import com.indocyber.entity.Cart;
import com.indocyber.repository.CartRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;

    public CartServiceImpl(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    @Override
    public void createCart(Cart cart) {
        cartRepository.save(cart);
    }
}
