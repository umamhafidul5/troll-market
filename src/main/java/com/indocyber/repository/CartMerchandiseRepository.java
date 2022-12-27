package com.indocyber.repository;

import com.indocyber.entity.CartMerchandise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CartMerchandiseRepository extends JpaRepository<CartMerchandise, Integer> {

    @Query("SELECT cm FROM CartMerchandise cm WHERE cm.cart.buyer.username = ?1")
    List<CartMerchandise> getCartListByUsername(String username);
}
