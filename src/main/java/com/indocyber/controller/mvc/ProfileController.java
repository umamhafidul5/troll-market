package com.indocyber.controller.mvc;

import com.indocyber.dto.TopUpDto;
import com.indocyber.entity.Account;
import com.indocyber.service.AccountService;
import com.indocyber.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
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
        model.addAttribute("topUp", new TopUpDto());
        return "profile-page";
    }

    @RequestMapping("/top-up")
    public String topUp(@Valid @ModelAttribute("topUp") TopUpDto topUp, BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("account", accountService.getAccount());
            model.addAttribute("transactionList",
                    transactionService.getTransactionsByAccount(accountService.getAccount()));
            model.addAttribute("account", accountService.getAccount());

            return "profile-page";
        }
        Account buyer = accountService.getAccount();
        accountService.topUp(buyer, topUp);

        return "redirect:/profile/index";
    }
}
