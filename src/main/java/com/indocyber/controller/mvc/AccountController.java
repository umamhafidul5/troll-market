package com.indocyber.controller.mvc;

import com.indocyber.dto.RegisterDto;
import com.indocyber.service.AccountService;
import com.indocyber.utility.Dropdown;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/account")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @PostMapping("/registerAccount")
    public String registerAccount(@ModelAttribute("account") RegisterDto dto) {

        if(dto.getRole().equals("Buyer")){
            accountService.registerBuyer(dto);
        } else if (dto.getRole().equals("Seller")) {
            accountService.registerSeller(dto);
        }else {
            accountService.registerAdmin(dto);
        }

        return "redirect:/my-login/showMyLoginPage";

    }

    @GetMapping("/registerBuyer")
    public String registerBuyer(Model model) {

        RegisterDto dto = new RegisterDto();
        dto.setRole("Buyer");

        model.addAttribute("account", dto);

        return "register-page";
    }

    @GetMapping("/registerSeller")
    public String registerSeller(Model model) {

        RegisterDto dto = new RegisterDto();
        dto.setRole("Seller");

        model.addAttribute("account", dto);

        return "register-page";
    }

    @GetMapping("/registerAdmin")
    public String registerAdmin(Model model) {

        RegisterDto dto = new RegisterDto();
        dto.setRole("Admin");

        model.addAttribute("account", dto);

        return "register-page";
    }
}
