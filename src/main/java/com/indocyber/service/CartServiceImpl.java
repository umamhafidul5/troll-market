package com.indocyber.service;

import com.indocyber.entity.*;
import com.indocyber.repository.AccountRepository;
import com.indocyber.repository.CartMerchandiseRepository;
import com.indocyber.repository.CartRepository;
import com.indocyber.repository.TransactionRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
public class CartServiceImpl implements CartService {

    private AccountService accountService;
    private AccountRepository accountRepository;
    private final CartRepository cartRepository;
    private final CartMerchandiseRepository cartMerchandiseRepository;
    private TransactionRepository transactionRepository;

    public CartServiceImpl(AccountService accountService, AccountRepository accountRepository, CartRepository cartRepository, CartMerchandiseRepository cartMerchandiseRepository, TransactionRepository transactionRepository) {
        this.accountService = accountService;
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
    }
}
