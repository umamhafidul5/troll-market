package com.indocyber.controller.restController;

import com.indocyber.dto.ShipmentDto;
import com.indocyber.dto.ShipmentDtoUpdate;
import com.indocyber.entity.Shipment;
import com.indocyber.service.ShipmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/shipment")
public class ShipmentRestController {

    @Autowired
    private ShipmentService shipmentService;

    @GetMapping("/getShipmentIndex")
    public ResponseEntity<List<Shipment>> getShipmentIndex(){

        List<Shipment> shipmentList = shipmentService.findAllShipment();

        return new ResponseEntity<>(shipmentList, HttpStatus.OK);
    }

    @PostMapping("/addShipment")
    public ResponseEntity<String> addShipment(@Valid @RequestBody ShipmentDto dto,
                                              BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(bindingResult.getFieldError().getDefaultMessage(), HttpStatus.BAD_REQUEST);
        }

        try {
            shipmentService.saveShipment(dto);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>("Add shipment Success", HttpStatus.CREATED);
    }

    @PutMapping("/update/id={id}") // Update
    public ResponseEntity<String> updateShipment(@PathVariable("id") Integer id,
                                                 @Valid @RequestBody ShipmentDtoUpdate dto,
                                                 BindingResult bindingResult) {

        ShipmentDto shipmentDto = new ShipmentDto();
        shipmentDto.setId(id);
        shipmentDto.setPrice(dto.getPrice());

        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(bindingResult.getFieldError().getDefaultMessage(), HttpStatus.BAD_REQUEST);
        }

        try {
            shipmentService.saveShipment(shipmentDto);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>("Update Shipment Success", HttpStatus.OK);
    }

    @DeleteMapping("/delete/id={id}") // Delete
    public ResponseEntity<String> deleteShipment(@PathVariable("id") Integer id) {

        shipmentService.deleteShipment(id);

        return new ResponseEntity<>("Delete Shipment Success", HttpStatus.OK);
    }

    @PatchMapping("/stopService/id={id}")
    public ResponseEntity<String> stopService(@PathVariable("id") Integer id){

        Shipment shipment = shipmentService.getShipmentById(id);
        if (shipment.isService()) {
            shipment.setService(false);
            shipmentService.updateShipment(shipment);
        }else {
            return new ResponseEntity<>("Shipment has Stopped Service", HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>("Stop Service Success", HttpStatus.OK);
    }

}
