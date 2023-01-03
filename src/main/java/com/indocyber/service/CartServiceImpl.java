package com.indocyber.service;

import com.indocyber.entity.Account;
import com.indocyber.entity.Cart;
import com.indocyber.entity.CartMerchandise;
import com.indocyber.repository.CartMerchandiseRepository;
import com.indocyber.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final CartMerchandiseRepository cartMerchandiseRepository;
    @Autowired
    private TransactionService transactionService;

    @Autowired
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

    @Override
    public Cart findByBuyer(Account account) {
        Optional<Cart> cart = cartRepository.findByBuyer(account);
        Cart tempCart = null;
        if (cart.isPresent()) {
            tempCart = cart.get();
        }
        return tempCart;
    }

    public BigDecimal countTotalPriceIncludeShipment() {
        List<CartMerchandise> cartMerchandiseList = cartMerchandiseRepository.getCartListByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        BigDecimal totalPrice = new BigDecimal(0);
        for(CartMerchandise cartMerchandise : cartMerchandiseList) {
            totalPrice = totalPrice.add((cartMerchandise
                    .getMerchandise().getPrice()
                    .multiply(BigDecimal.valueOf(cartMerchandise.getQuantity())))
                    .add(cartMerchandise.getShipment().getPrice()));
        }
        return totalPrice;
    }

    @Override
    public void purchasingMerchandiseOnCart() {
        transactionService.putCartToTransaction();
    }
}
