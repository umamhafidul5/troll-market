package com.indocyber.service;

import com.indocyber.dto.TransactionViewDto;
import com.indocyber.entity.Account;
import com.indocyber.entity.Transaction;
import org.springframework.data.domain.Page;

import java.math.BigDecimal;
import java.util.List;

public interface TransactionService {
    List<Transaction> getTransactionsByAccount(Account account);

    List<TransactionViewDto> getTransactionsViewByAccount(Account account);

    List<Transaction> getAllTransactionList();

    Account findById(String username);

    List<Transaction> searchTransaction(String usernameSeller, String usernameBuyer);

    void putCartToTransaction();

    BigDecimal countTotalPriceIncludeShipment();
}
