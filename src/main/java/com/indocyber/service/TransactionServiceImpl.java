package com.indocyber.service;

import com.indocyber.entity.Account;
import com.indocyber.entity.Transaction;
import com.indocyber.repository.AccountRepository;
import com.indocyber.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;

    private final AccountRepository accountRepository;

    @Autowired
    public TransactionServiceImpl(TransactionRepository transactionRepository, AccountRepository accountRepository) {
        this.transactionRepository = transactionRepository;
        this.accountRepository = accountRepository;
    }

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
}
