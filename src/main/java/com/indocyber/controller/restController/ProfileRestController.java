package com.indocyber.controller.restController;

import com.indocyber.dto.*;
import com.indocyber.entity.Account;
import com.indocyber.entity.Transaction;
import com.indocyber.service.AccountService;
import com.indocyber.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/profile")
public class ProfileRestController {

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private AccountService accountService;

    @GetMapping("/getProfileIndex")
    public ResponseEntity<ProfileViewDto> getProfileIndex(){

        Account account = accountService.getAccount();
        List<TransactionViewDto> transactionList = transactionService.getTransactionsViewByAccount(account);
        ProfileViewDto profileViewDto = new ProfileViewDto(account.getFirstName(), account.getLastName(),
                account.getRole(), account.getAddress(), account.getBalance(), transactionList);

        return new ResponseEntity<>(profileViewDto, HttpStatus.OK);
    }

    @PostMapping("/topUp")
    public ResponseEntity<String> topUp(@Valid @RequestBody TopUpDto dto, BindingResult bindingResult){

        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(bindingResult.getFieldError().getDefaultMessage(), HttpStatus.BAD_REQUEST);
        }

        Account buyer = accountService.getAccount();
        if(buyer.getRole().equals("Buyer")){
            accountService.topUp(buyer, dto);
        }else {
            return new ResponseEntity<>("top up just for buyer", HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>("top up success", HttpStatus.OK);
    }
}
