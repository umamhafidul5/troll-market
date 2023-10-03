package com.indocyber.repository;

import com.indocyber.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AccountRepository extends JpaRepository<Account, String> {

    @Query("""
            SELECT COUNT(acc)
            FROM Account AS acc
            WHERE acc.username = :username
            """)
    Long count(@Param("username") String username);

    List<Account> getAccountsByRole(String role);

    Account getAccountByUsername(String username);
}
