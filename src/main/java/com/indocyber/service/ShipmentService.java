package com.indocyber.service;

import com.indocyber.entity.Shipment;

import java.util.List;

public interface ShipmentService {
    List<Shipment> getAllShipment();
    Shipment getShipmentById(int id);

    void saveShipment(Shipment shipment);
}
