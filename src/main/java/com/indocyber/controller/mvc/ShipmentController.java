package com.indocyber.controller.mvc;

import com.indocyber.dto.ShipmentDto;
import com.indocyber.entity.Shipment;
import com.indocyber.service.AccountService;
import com.indocyber.service.ShipmentService;
import com.indocyber.service.ShipmentServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Locale;

@Controller
@RequestMapping("/shipment")
public class ShipmentController {

    ShipmentService shipmentService;

    @Autowired
    private AccountService accountService;

    @Autowired
    public ShipmentController(ShipmentService shipmentService) {
        this.shipmentService = shipmentService;
    }

    @GetMapping("/index")
    public String viewShipmentPage(
            Model model,
            @RequestParam(name = "stopSuccess", required = false) Integer serviceId,
            @RequestParam(name = "deletion", required = false) String serviceName
    ) {
        model.addAttribute("account", accountService.getAccount());
        model.addAttribute("shipmentDto", new ShipmentDto());
        if(serviceId != null) {
            model.addAttribute("service", shipmentService.getShipmentById(serviceId).getName());
        }

        if(serviceName != null) {
            model.addAttribute("deletedName", serviceName);
        }
        model.addAttribute("shipmentList", shipmentService.getAllShipment());
        return "shipment-page";
    }

    @PostMapping("/stopService")
    public String stopShipmentService(@RequestParam(name = "id") int id) {
        try {
            Shipment shipment = shipmentService.getShipmentById(id);
            shipment.setService(false);
            shipmentService.saveShipment(shipment);
        } catch (Exception e) {
            System.out.println(e);
        }

        return "redirect:/shipment/index?stopSuccess=" + id;
    }

    @PostMapping("/delete")
    public String deleteShipment(@RequestParam(name = "id") int id) {
        Shipment shipment = shipmentService.getShipmentById(id);
        try {
            shipmentService.deleteShipment(id);
        } catch (Exception e) {
            System.out.println(e);
        }

        return "redirect:/shipment/index?deletion=" + shipment.getName();
    }
}
