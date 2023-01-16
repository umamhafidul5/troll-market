package com.indocyber.controller.restController;

import com.indocyber.dto.MerchandiseDto;
import com.indocyber.dto.ShipmentDto;
import com.indocyber.dto.ShipmentDtoUpdate;
import com.indocyber.entity.Account;
import com.indocyber.entity.Merchandise;
import com.indocyber.entity.Shipment;
import com.indocyber.service.AccountService;
import com.indocyber.service.MerchandiseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/merchandise")
public class MerchandiseRestController {

    @Autowired
    private MerchandiseService merchandiseService;

    @Autowired
    private AccountService accountService;

    @GetMapping("/getMerchandiseIndex")
    public ResponseEntity<List<Merchandise>> getMerchandiseIndex(){

        Account account = accountService.getAccount();
        List<Merchandise> merchandiseSeller = merchandiseService.getMerchandiseSeller(account);

        return new ResponseEntity<>(merchandiseSeller, HttpStatus.OK);
    }

    @PostMapping("/addMerchandise")
    public ResponseEntity<String> addMerchandise(@Valid @RequestBody MerchandiseDto dto,
                                                 BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(bindingResult.getFieldError().getDefaultMessage(), HttpStatus.BAD_REQUEST);
        }

        try {
            merchandiseService.saveProduct(dto);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>("Add Merchandise Success", HttpStatus.CREATED);
    }

    @PutMapping("/update/id={id}") // Update
    public ResponseEntity<String> updateMerchandise(@PathVariable("id") Integer id,
                                                    @Valid @RequestBody MerchandiseDto dto,
                                                    BindingResult bindingResult) {
        dto.setId(id);

        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(bindingResult.getFieldError().getDefaultMessage(), HttpStatus.BAD_REQUEST);
        }

        try {
            merchandiseService.saveProduct(dto);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>("Update Merchandise Success", HttpStatus.OK);
    }

    @DeleteMapping("/delete/id={id}") // Delete
    public ResponseEntity<String> deleteMerchandise(@PathVariable("id") Integer id) {

        merchandiseService.deleteProduct(id);

        return new ResponseEntity<>("Delete Merchandise Success", HttpStatus.OK);
    }

    @PatchMapping("/discontinue/id={id}")
    public ResponseEntity<String> discontinue(@PathVariable("id") Integer id){

        Merchandise merchandise = merchandiseService.findById(id);
        if (!merchandise.getIsDiscontinue()) {
            merchandise.setIsDiscontinue(true);
            merchandiseService.saveMerchandise(merchandise);
        }else {
            return new ResponseEntity<>("Merchandise has Discontinue", HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>("Discontinue Success", HttpStatus.OK);
    }

    @GetMapping("/info/id={id}")
    public ResponseEntity<Merchandise> infoMerchandise(@PathVariable("id") Integer id){

        Merchandise merchandise = merchandiseService.findById(id);

        return new ResponseEntity<>(merchandise, HttpStatus.OK);
    }

}
