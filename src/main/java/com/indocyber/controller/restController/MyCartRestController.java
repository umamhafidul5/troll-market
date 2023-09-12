package com.indocyber.controller.restController;

import com.indocyber.entity.Account;
import com.indocyber.entity.CartMerchandise;
import com.indocyber.service.AccountService;
import com.indocyber.service.CartMerchandiseService;
import com.indocyber.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/myCart")
public class MyCartRestController {

    @Autowired
    private CartMerchandiseService cartMerchandiseService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private TransactionService transactionService;

    @GetMapping("/getMyCartIndex")
    public ResponseEntity<List<CartMerchandise>> getShipmentIndex(){

        Account account = accountService.getAccount();
        List<CartMerchandise> cartListByUsername = cartMerchandiseService.getCartListByUsername(account.getUsername());

        return new ResponseEntity<>(cartListByUsername, HttpStatus.OK);
    }

    @DeleteMapping("/remove/id={id}")
    public ResponseEntity<String> removeCartMerchandise(@PathVariable("id") Integer id) {

        cartMerchandiseService.deleteCartMerchandise(id);

        return new ResponseEntity<>("Remove Cart Merchandise Success", HttpStatus.OK);
    }

    @GetMapping("/purchaseAll")
    public ResponseEntity<String> purchaseAll () throws MessagingException, IOException {

        Account account = accountService.getAccount();
        if(account.getBalance().compareTo(transactionService.countTotalPriceIncludeShipment()) >= 0) {
            transactionService.putCartToTransaction();
        }else {
            return new ResponseEntity<>("purchase failed, insufficient balance", HttpStatus.OK);
        }

        return new ResponseEntity<>("Purchase Success", HttpStatus.OK);
    }
}
