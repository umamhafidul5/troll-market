package com.indocyber.controller.restController;

import com.indocyber.dto.RegisterAdminDto;
import com.indocyber.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/admin")
public class AdminRestController {

    @Autowired
    private AccountService accountService;

    @PostMapping("/registerAdmin")
    public ResponseEntity<String> addAdmin(@Valid @RequestBody RegisterAdminDto dto,
                                           BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(bindingResult.getFieldError().getDefaultMessage(), HttpStatus.BAD_REQUEST);
        }

        try {
            accountService.registerAdmin(dto);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>("Register Admin Success", HttpStatus.CREATED);
    }
}
