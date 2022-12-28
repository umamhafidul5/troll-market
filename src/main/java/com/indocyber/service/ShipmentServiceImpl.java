package com.indocyber.service;

import com.indocyber.dto.ShipmentDto;
import com.indocyber.entity.Shipment;
import com.indocyber.repository.ShipmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class ShipmentServiceImpl implements ShipmentService {

    ShipmentRepository shipmentRepository;

    @Autowired
    public ShipmentServiceImpl(ShipmentRepository shipmentRepository) {
        this.shipmentRepository = shipmentRepository;
    }

    @Override
    public List<Shipment> getAllShipment() {
        return shipmentRepository.findAll();
    }

    @Override
    public Shipment getShipmentById(int id) {
        return shipmentRepository.findById(id).get();
    }

    @Override
    public void saveShipment(ShipmentDto shipmentDto) {
        Shipment shipment = new Shipment();
        shipment.setName(shipmentDto.getName());
        shipment.setPrice(shipmentDto.getPrice());
        shipment.setService(shipmentDto.isService());
        shipmentRepository.save(shipment);
    }

    @Override
    public void updateShipment(Shipment shipment) {
        shipmentRepository.save(shipment);
    }

    @Override
    public void deleteShipment(int id) {
        shipmentRepository.deleteById(id);
    }
}
