package com.indocyber.repository;

import com.indocyber.entity.Shipment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ShipmentRepository extends JpaRepository<Shipment, Integer> {

    Optional<Shipment> findByName(String name);
}
