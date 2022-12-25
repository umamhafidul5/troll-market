package com.indocyber.repository;

import com.indocyber.entity.Merchandise;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MerchandiseRepository extends JpaRepository<Merchandise, Integer> {
}
