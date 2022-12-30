package com.indocyber.service;

import com.indocyber.entity.Account;
import com.indocyber.entity.Cart;
import com.indocyber.entity.CartMerchandise;
import com.indocyber.repository.AccountRepository;
import com.indocyber.repository.CartMerchandiseRepository;
import com.indocyber.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CartServiceImpl implements CartService {

    private final AccountRepository accountRepository;
    private final CartRepository cartRepository;
    private final CartMerchandiseRepository cartMerchandiseRepository;

    @Autowired
    public CartServiceImpl(AccountRepository accountRepository, CartRepository cartRepository, CartMerchandiseRepository cartMerchandiseRepository) {
        this.accountRepository = accountRepository;
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

    @Override
    public Cart findByBuyer(Account account) {
        Optional<Cart> cart = cartRepository.findByBuyer(account);
        Cart tempCart = null;
        if(cart.isPresent()){
            tempCart = cart.get();
        }
        return tempCart;
    }
}
