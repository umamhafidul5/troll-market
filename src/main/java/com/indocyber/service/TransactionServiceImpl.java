package com.indocyber.service;

import com.indocyber.dto.TransactionViewDto;
import com.indocyber.entity.Account;
import com.indocyber.entity.CartMerchandise;
import com.indocyber.entity.Transaction;
import com.indocyber.repository.AccountRepository;
import com.indocyber.repository.CartMerchandiseRepository;
import com.indocyber.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.transaction.Transactional;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
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
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    @Autowired
    private EmailService emailService;

    private void setData(String key, Object value) {
//        redisTemplate.opsForValue().set(key, value);
        redisTemplate.opsForValue().set(key, value);
    }

    private Object getData(String key) {
        return redisTemplate.opsForValue().get(key);
    }

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
    public List<TransactionViewDto> getTransactionsViewByAccount(Account account) {
        return transactionRepository.getTransactionViewDtoByBuyer(account);
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
//    @CachePut(value = "dataList", key = "#usernameSeller + ':' + #usernameBuyer")
    public List<Transaction> searchTransaction(String usernameSeller, String usernameBuyer) {
//        System.out.println("service");
//        String key = "dataList" + usernameSeller + ":" + usernameBuyer;
//        String key = usernameSeller + ":" + usernameBuyer;

//        List<Transaction> dataList = (List<Transaction>) getData(key);
//        List<Transaction> dataList = (List<Transaction>) getData("dataList:");


//        System.out.println(dataList);

//        if (dataList == null) {
//
//            dataList = transactionRepository.searchTransaction(usernameSeller, usernameBuyer);
//            setData(key, dataList);
//        }
//        return dataList;


        return transactionRepository.searchTransaction(usernameSeller, usernameBuyer);
    }

    @Override
    public void putCartToTransaction() throws MessagingException, IOException {
        // Mengurangi saldo buyer
        Account account = accountService.getAccount();
        account.setBalance(account.getBalance().subtract(countTotalPriceIncludeShipment()));
        accountService.saveBuyer(account);
        // -----


        // Menambah saldo penjual
        List<CartMerchandise> cartMerchandiseList = cartMerchandiseRepository.getCartListByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        for(CartMerchandise cartMerchandise : cartMerchandiseList) {
            Account sellerAccount = accountRepository.findById(cartMerchandise.getMerchandise().getSeller().getUsername()).orElseThrow();
            BigDecimal totalPrice = (cartMerchandise.getMerchandise()
                    .getPrice().multiply(BigDecimal.valueOf(cartMerchandise.getQuantity())));
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

        emailService.sendEmail("umamhafidul5@gmail.com", "Terima Kasih Sudah Berbelanja!", Arrays.toString(cartMerchandiseList.stream()
                .map(s -> s.getId() +
                        "|" + s.getMerchandise().getName()+
                        "|" + (s.getMerchandise().getPrice().multiply(new BigDecimal(s.getQuantity()))))
                .toArray()));
        // -----
    }

    public BigDecimal countTotalPriceIncludeShipment() {
        List<CartMerchandise> cartMerchandiseList = cartMerchandiseRepository.getCartListByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        BigDecimal totalPrice = new BigDecimal(0);
        for(CartMerchandise cartMerchandise : cartMerchandiseList) {
            totalPrice = totalPrice.add((cartMerchandise.getMerchandise()
                    .getPrice().multiply(BigDecimal.valueOf(cartMerchandise.getQuantity())))
                    .add(cartMerchandise.getShipment().getPrice()));
        }
        return totalPrice;
    }
}
