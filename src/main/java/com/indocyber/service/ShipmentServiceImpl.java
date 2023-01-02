package com.indocyber.service;

import com.indocyber.dto.ShipmentDto;
import com.indocyber.entity.Shipment;
import com.indocyber.repository.ShipmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

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
        return shipmentRepository.getAllShipmentService();
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

    @Override
    public Shipment getShipmentByName(String name) {
        Optional<Shipment> shipment = shipmentRepository.findByName(name);
        Shipment tempShipment = null;
        if(shipment.isPresent()){
            tempShipment = shipment.get();
        }
        return tempShipment;
    }
}
