package com.indocyber.repository;

import com.indocyber.entity.Account;
import com.indocyber.entity.Merchandise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MerchandiseRepository extends JpaRepository<Merchandise, Integer> {

    List<Merchandise> getMerchandisesByNameAndCategoryAndDescription(String name, String category, String description);

    @Query("""
            SELECT mer
            FROM Merchandise AS mer
            WHERE (mer.name LIKE %:name% AND mer.category LIKE %:category% 
            AND mer.description LIKE %:description% AND mer.isDiscontinue = false )
            """)
    List<Merchandise> searchMerchandises(String name, String category, String description);

    List<Merchandise> getAllBySeller(Account seller);
}
