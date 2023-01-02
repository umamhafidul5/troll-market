package com.indocyber.controller.mvc;

import com.indocyber.service.AccountService;
import com.indocyber.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/history")
public class HistoryController {

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private AccountService accountService;

    @GetMapping("/index")
    public String index(@RequestParam(name = "seller", required = false, defaultValue = "") String usernameSeller,
                        @RequestParam(name = "buyer", required = false, defaultValue = "") String usernameBuyer,
                        Model model){

        model.addAttribute("buyerList", accountService.getAccountsByRole("Buyer"));
        model.addAttribute("sellerList", accountService.getAccountsByRole("Seller"));
        model.addAttribute("account", accountService.getAccount());
        model.addAttribute("transactionList", transactionService.searchTransaction(usernameSeller,usernameBuyer));

        return "history-page";
    }

}
