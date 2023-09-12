package com.indocyber.controller.restController;

import com.indocyber.entity.Account;
import com.indocyber.entity.Shipment;
import com.indocyber.entity.Transaction;
import com.indocyber.service.AccountService;
import com.indocyber.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/history")
public class HistoryRestController {

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private AccountService accountService;

    @GetMapping(
            value = {
                    "/getHistoryIndex"
            })
    public ResponseEntity<List<Transaction>> getHistoryIndex
            (@RequestParam(value = "seller", required = false) String usernameSeller,
             @RequestParam(value = "buyer", required = false) String usernameBuyer){

        usernameSeller = usernameSeller == null ? "" : usernameSeller;
        usernameBuyer = usernameBuyer == null ? "" : usernameBuyer;

        List<Transaction> transactionList = transactionService.searchTransaction(usernameSeller, usernameBuyer);

        return new ResponseEntity<>(transactionList, HttpStatus.OK);
    }

    @GetMapping("/getAccountBy")
    public ResponseEntity<List<Account>> getAccountListBy(@RequestParam(value = "role") String role) {

        return new ResponseEntity<>( accountService.getAccountsByRole(role), HttpStatus.OK);
    }
}
