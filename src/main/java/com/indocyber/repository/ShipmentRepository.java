package com.indocyber.repository;

import com.indocyber.entity.Shipment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ShipmentRepository extends JpaRepository<Shipment, Integer> {

    Optional<Shipment> findByName(String name);

    @Query("""
            SELECT shi
            FROM Shipment AS shi
            WHERE shi.service IS true
            """)
    List<Shipment> getAllShipmentService();
}
