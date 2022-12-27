package com.indocyber.repository;

import com.indocyber.entity.Account;
import com.indocyber.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Integer> {

    List<Transaction> getTransactionsByBuyer(Account account);

    @Query("""
            SELECT tra
            FROM Transaction AS tra
            WHERE (tra.merchandise.seller.username = :usernameSeller AND tra.buyer.username = :usernameBuyer)
            """)
    List<Transaction> searchTransaction(@Param("usernameSeller") String usernameSeller,
                                        @Param("usernameBuyer") String usernameBuyer);
//    @Query("""
//            SELECT tra
//            FROM Transaction AS tra
//            WHERE (tra.merchandise.seller.username = :usernameSeller AND tra.buyer.username = :usernameBuyer)
//            OR tra.merchandise.seller.username = :usernameSeller
//            OR tra.buyer.username = :usernameBuyer
//            """)
//    List<Transaction> searchTransaction(@Param("usernameSeller") String usernameSeller,
//                                        @Param("usernameBuyer") String usernameBuyer);
}
