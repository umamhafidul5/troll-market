package com.indocyber.service;

import com.indocyber.entity.Account;
import com.indocyber.entity.Transaction;
import com.indocyber.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;

    @Autowired
    public TransactionServiceImpl(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    @Override
    public List<Transaction> getTransactionsByAccount(Account account) {
        return transactionRepository.getTransactionsByBuyer(account);
    }
}
