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
//        model.addAttribute("buyerList", accountService.getAccountRole("Buyer"));
//        model.addAttribute("sellerList", accountService.getAccountRole("Seller"));

        return "history-page";
    }

    @GetMapping("/searchByAccount")
    public String searchByAccount(@RequestParam("username") String username, Model model){
        Account account = transactionService.findById(username);
        model.addAttribute("transactionList", transactionService.getTransactionsByAccount(account));
        return "redirect:/history/index";
    }
}
