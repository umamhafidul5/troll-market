package com.indocyber.repository;

import com.indocyber.entity.Account;
import com.indocyber.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Integer> {

    Optional<Cart> findByBuyer(Account account);
}
