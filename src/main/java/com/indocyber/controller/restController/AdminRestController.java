package com.indocyber.controller.restController;

import com.indocyber.dto.RegisterAdminDto;
import com.indocyber.dto.StringDto;
import com.indocyber.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Objects;

@RestController
@RequestMapping("/api/admin")
public class AdminRestController {

    @Autowired
    private AccountService accountService;

    @PostMapping("/registerAdmin")
    public ResponseEntity<StringDto> addAdmin(@Valid @RequestBody RegisterAdminDto dto,
                                           BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            try {
                return new ResponseEntity<>(new StringDto(Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage()), HttpStatus.BAD_REQUEST);
            } catch (Exception e) {

                return new ResponseEntity<>(new StringDto(Objects.requireNonNull(bindingResult.getGlobalError()).getDefaultMessage()), HttpStatus.BAD_REQUEST);

            }
        }

        try {
            accountService.registerAdmin(dto);
        } catch (Exception e) {
            return new ResponseEntity<>(new StringDto(e.getMessage()), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(new StringDto("Register Admin Success"), HttpStatus.CREATED);
    }
}
