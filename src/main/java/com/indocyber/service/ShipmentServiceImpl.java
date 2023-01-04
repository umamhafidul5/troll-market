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

    private ShipmentRepository shipmentRepository;

    @Autowired
    public ShipmentServiceImpl(ShipmentRepository shipmentRepository) {
        this.shipmentRepository = shipmentRepository;
    }

    @Override
    public List<Shipment> getAllShipmentService() {
        return shipmentRepository.getAllShipmentService();
    }

    @Override
    public List<Shipment> findAllShipment() {
        return shipmentRepository.findAll();
    }

    @Override
    public Shipment getShipmentById(int id) {
        return shipmentRepository.findById(id).get();
    }

    @Override
    public void saveShipment(ShipmentDto shipmentDto) {
        Shipment shipment = new Shipment();
        if(shipmentDto.getId() == 0){
            shipment.setName(shipmentDto.getName());
            shipment.setPrice(shipmentDto.getPrice());
            shipment.setService(shipmentDto.isService());

        }else {
            Optional<Shipment> optShipment = shipmentRepository.findById(shipmentDto.getId());
            if(optShipment.isPresent()){
                shipment = optShipment.get();
//                shipment.setId(shipmentDto.getId());
                shipment.setPrice(shipmentDto.getPrice());
//                shipment.setName(shipmentDto.getName());
//                shipment.setService(shipmentDto.isService());
            }
        }
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

    @Override
    public ShipmentDto findById(int id) {
        Optional<Shipment> optShipment = shipmentRepository.findById(id);
        ShipmentDto shipmentDto = new ShipmentDto();
        if(optShipment.isPresent()){
            Shipment shipment = optShipment.get();
            shipmentDto.setId(shipment.getId());
            shipmentDto.setName(shipment.getName());
            shipmentDto.setPrice(shipment.getPrice());
            shipmentDto.setService(shipment.isService());
        }
        return shipmentDto;
    }
}
