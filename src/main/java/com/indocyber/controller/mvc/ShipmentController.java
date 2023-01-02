package com.indocyber.controller.mvc;

import com.indocyber.dto.ShipmentDto;
import com.indocyber.entity.Shipment;
import com.indocyber.service.AccountService;
import com.indocyber.service.ShipmentService;
import com.indocyber.service.ShipmentServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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

    @GetMapping("/stopService")
    public String stopShipmentService(@RequestParam(name = "id") int id) {
        try {
            Shipment shipment = shipmentService.getShipmentById(id);
            if (shipment.isService()) {
                shipment.setService(false);
                shipmentService.updateShipment(shipment);
            }
        } catch (Exception e) {
            System.out.println(e);
        }

        return "redirect:/shipment/index?stopSuccess=" + id;
    }

    @GetMapping("/delete")
    public String deleteShipment(@RequestParam(name = "id") int id) {
        Shipment shipment = shipmentService.getShipmentById(id);
        try {
            shipmentService.deleteShipment(id);
        } catch (Exception e) {
            System.out.println(e);
        }

        return "redirect:/shipment/index?deletion=" + shipment.getName();
    }

    @PostMapping("/add")
    public String addShipment(
            @Valid @ModelAttribute(name = "shipmentDto") ShipmentDto shipmentDto,
            BindingResult bindingResult,
            Model model,
            @RequestParam(name = "stopSuccess", required = false) Integer serviceId,
            @RequestParam(name = "deletion", required = false) String serviceName
    ) {

        if (bindingResult.hasErrors()) {


            model.addAttribute("account", accountService.getAccount());
            if(serviceId != null) {
                model.addAttribute("service", shipmentService.getShipmentById(serviceId).getName());
            }

            if(serviceName != null) {
                model.addAttribute("deletedName", serviceName);
            }
            model.addAttribute("shipmentList", shipmentService.getAllShipment());

            return "shipment-page";
        }

        try {
            shipmentService.saveShipment(shipmentDto);
        } catch (Exception e) {
            System.out.println(e);
        }
        return "redirect:/shipment/index";
    }
}
