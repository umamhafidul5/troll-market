package com.indocyber.controller.restController;

import com.indocyber.entity.Shipment;
import com.indocyber.entity.Transaction;
import com.indocyber.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/history")
public class HistoryRestController {

    @Autowired
    private TransactionService transactionService;

    @GetMapping(
            value = {
                    "/getHistoryIndex",
                    "/getHistoryIndex/seller={seller}",
                    "/getHistoryIndex/buyer={buyer}",
                    "/getHistoryIndex/seller={seller}/buyer={buyer}",
            })
    public ResponseEntity<List<Transaction>> getHistoryIndex
            (@PathVariable(value = "seller", required = false) String usernameSeller,
             @PathVariable(value = "buyer", required = false) String usernameBuyer){

        usernameSeller = usernameSeller == null ? "" : usernameSeller;
        usernameBuyer = usernameBuyer == null ? "" : usernameBuyer;

        List<Transaction> transactionList = transactionService.searchTransaction(usernameSeller, usernameBuyer);

        return new ResponseEntity<>(transactionList, HttpStatus.OK);
    }
}
