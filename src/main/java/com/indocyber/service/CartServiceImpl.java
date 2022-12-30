package com.indocyber.service;

<<<<<<< HEAD
import com.indocyber.entity.Account;
import com.indocyber.entity.Cart;
import com.indocyber.entity.CartMerchandise;
import com.indocyber.repository.AccountRepository;
import com.indocyber.repository.CartMerchandiseRepository;
import com.indocyber.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
=======
import com.indocyber.entity.*;
import com.indocyber.repository.AccountRepository;
import com.indocyber.repository.CartMerchandiseRepository;
import com.indocyber.repository.CartRepository;
import com.indocyber.repository.TransactionRepository;
import org.springframework.security.core.context.SecurityContextHolder;
>>>>>>> d72334a2a91c8a613746e2de16603348b42a34e8
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CartServiceImpl implements CartService {

<<<<<<< HEAD
    private final AccountRepository accountRepository;
=======
    private AccountService accountService;
    private AccountRepository accountRepository;
>>>>>>> d72334a2a91c8a613746e2de16603348b42a34e8
    private final CartRepository cartRepository;
    private final CartMerchandiseRepository cartMerchandiseRepository;
    private TransactionRepository transactionRepository;

<<<<<<< HEAD
    @Autowired
    public CartServiceImpl(AccountRepository accountRepository, CartRepository cartRepository, CartMerchandiseRepository cartMerchandiseRepository) {
=======
    public CartServiceImpl(AccountService accountService, AccountRepository accountRepository, CartRepository cartRepository, CartMerchandiseRepository cartMerchandiseRepository, TransactionRepository transactionRepository) {
        this.accountService = accountService;
>>>>>>> d72334a2a91c8a613746e2de16603348b42a34e8
        this.accountRepository = accountRepository;
        this.cartRepository = cartRepository;
        this.cartMerchandiseRepository = cartMerchandiseRepository;
        this.transactionRepository = transactionRepository;
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
<<<<<<< HEAD
    public Cart findByBuyer(Account account) {
        Optional<Cart> cart = cartRepository.findByBuyer(account);
        Cart tempCart = null;
        if(cart.isPresent()){
            tempCart = cart.get();
        }
        return tempCart;
=======
    public BigDecimal countTotalPriceIncludeShipment() {
        List<CartMerchandise> cartMerchandiseList = cartMerchandiseRepository.getCartListByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        BigDecimal totalPrice = new BigDecimal(0);
        for(CartMerchandise cartMerchandise : cartMerchandiseList) {
            totalPrice = (cartMerchandise.getMerchandise().getPrice().multiply(BigDecimal.valueOf(cartMerchandise.getQuantity()))).add(cartMerchandise.getShipment().getPrice());
        }
        return totalPrice;
    }

    @Override
    public void purchasingMerchandiseOnCart() {
        // Mengurangi saldo buyer
        Account account = accountService.getAccount();
        account.setBalance(account.getBalance().subtract(countTotalPriceIncludeShipment()));
        accountService.saveBuyer(account);
        // -----


        // Menambah saldo penjual
        List<CartMerchandise> cartMerchandiseList = cartMerchandiseRepository.getCartListByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        for(CartMerchandise cartMerchandise : cartMerchandiseList) {
            Account sellerAccount = accountRepository.findById(cartMerchandise.getMerchandise().getSeller().getUsername()).orElseThrow();
            BigDecimal totalPrice = (cartMerchandise.getMerchandise().getPrice().multiply(BigDecimal.valueOf(cartMerchandise.getQuantity()))).add(cartMerchandise.getShipment().getPrice());
            sellerAccount.setBalance(sellerAccount.getBalance().add(totalPrice));
            accountService.saveBuyer(sellerAccount);
        }
        // -----


        // Membuat transaction tiap barang
        for(CartMerchandise cartMerchandise : cartMerchandiseList) {
            transactionRepository.save(new Transaction(
                    LocalDate.now(),
                    cartMerchandise.getMerchandise(),
                    cartMerchandise.getMerchandise().getName(),
                    cartMerchandise.getQuantity(),
                    cartMerchandise.getShipment(),
                    (cartMerchandise.getMerchandise().getPrice().multiply(BigDecimal.valueOf(cartMerchandise.getQuantity()))).add(cartMerchandise.getShipment().getPrice()),
                    accountService.getAccount()
                    ));
        }
        // -----


        // Menghapus CartMerchandise pembeli
        for(CartMerchandise cartMerchandise : cartMerchandiseList) {
            cartMerchandiseRepository.deleteById(cartMerchandise.getId());
        }
        // -----
>>>>>>> d72334a2a91c8a613746e2de16603348b42a34e8
    }
}
