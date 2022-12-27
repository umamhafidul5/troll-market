package com.indocyber.controller.mvc;

import com.indocyber.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/home")
public class HomeController {

    @Autowired
    private AccountService accountService;

    @RequestMapping("/index")
    public String index(Model model) {

        model.addAttribute("account", accountService.getAccount());
        return "home-page";
    }

//    @GetMapping("/access-denied")
//    public String showAccessDenied() {
//
//        return "access-denied";
//    }
}
