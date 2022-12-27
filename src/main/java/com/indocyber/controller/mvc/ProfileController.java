package com.indocyber.controller.mvc;

import com.indocyber.service.AccountService;
import com.indocyber.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;

@Controller
@RequestMapping("/profile")
public class ProfileController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private TransactionService transactionService;
    @RequestMapping("/index")
    public String index(Model model) {

        model.addAttribute("account", accountService.getAccount());
        model.addAttribute("transactionList",
                transactionService.getTransactionsByAccount(accountService.getAccount()));
        model.addAttribute("account", accountService.getAccount());
        return "profile-page";
    }

    @RequestMapping("/top-up")
    public String topUp(@RequestParam("topUp") String topUp) {
        BigDecimal topUpAmount = new BigDecimal(topUp);
        return "redirect:/profile/index";
    }
}
