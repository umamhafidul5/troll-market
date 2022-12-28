package com.indocyber.repository;

import com.indocyber.entity.Merchandise;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MerchandiseRepository extends JpaRepository<Merchandise, Integer> {

    List<Merchandise> getMerchandisesByNameAndCategoryAndDescription(String name, String category, String description);
}
