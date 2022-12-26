package com.indocyber.service;

import com.indocyber.entity.Account;
import com.indocyber.entity.Transaction;

import java.util.List;

public interface TransactionService {
    List<Transaction> getTransactionsByAccount(Account account);
}
