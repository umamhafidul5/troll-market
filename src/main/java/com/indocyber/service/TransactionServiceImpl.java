package com.indocyber.service;

import com.indocyber.entity.Account;
import com.indocyber.entity.CartMerchandise;
import com.indocyber.entity.Transaction;
import com.indocyber.repository.AccountRepository;
import com.indocyber.repository.CartMerchandiseRepository;
import com.indocyber.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private AccountService accountService;
    @Autowired
    private CartMerchandiseRepository cartMerchandiseRepository;


//    @Autowired
//    public TransactionServiceImpl(TransactionRepository transactionRepository, AccountRepository accountRepository, AccountService accountService, CartMerchandiseRepository cartMerchandiseRepository) {
//        this.transactionRepository = transactionRepository;
//        this.accountRepository = accountRepository;
//        this.accountService = accountService;
//        this.cartMerchandiseRepository = cartMerchandiseRepository;
//    }
    public TransactionServiceImpl() {}

    @Override
    public List<Transaction> getTransactionsByAccount(Account account) {
        return transactionRepository.getTransactionsByBuyer(account);
    }

    @Override
    public List<Transaction> getAllTransactionList() {
        return transactionRepository.findAll();
    }

    @Override
    public Account findById(String username) {
        Optional<Account> account = accountRepository.findById(username);
        Account tempAccount = null;
        if(account.isPresent()){
            tempAccount = account.get();
        }
        return tempAccount;
    }

    @Override
    public List<Transaction> searchTransaction(String usernameSeller, String usernameBuyer) {
        return transactionRepository.searchTransaction(usernameSeller, usernameBuyer);
    }

    @Override
    public void putCartToTransaction() {
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

    public BigDecimal countTotalPriceIncludeShipment() {
        List<CartMerchandise> cartMerchandiseList = cartMerchandiseRepository.getCartListByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        BigDecimal totalPrice = new BigDecimal(0);
        for(CartMerchandise cartMerchandise : cartMerchandiseList) {
            totalPrice = (cartMerchandise.getMerchandise().getPrice().multiply(BigDecimal.valueOf(cartMerchandise.getQuantity()))).add(cartMerchandise.getShipment().getPrice());
        }
        return totalPrice;
    }
}
