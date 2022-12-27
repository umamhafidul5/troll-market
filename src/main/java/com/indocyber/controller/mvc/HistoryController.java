package com.indocyber.controller.mvc;

import com.indocyber.entity.Account;
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
    public String index(Model model){

        model.addAttribute("transactionList", transactionService.getAllTransactionList());
        model.addAttribute("buyerList", accountService.getAccountsByRole("Buyer"));
        model.addAttribute("sellerList", accountService.getAccountsByRole("Seller"));
        model.addAttribute("account", accountService.getAccount());

        return "history-page";
    }

    @GetMapping("/searchHistory")
    public String searchHistory(@RequestParam("usernameSeller") String usernameSeller,
                                @RequestParam("usernameBuyer") String usernameBuyer, Model model){
        model.addAttribute("transactionList", transactionService.searchTransaction(usernameSeller,usernameBuyer));
        model.addAttribute("buyerList", accountService.getAccountsByRole("Buyer"));
        model.addAttribute("sellerList", accountService.getAccountsByRole("Seller"));
        model.addAttribute("account", accountService.getAccount());
        return "history-page";
    }
}
