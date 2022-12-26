package com.indocyber.controller.mvc;

import com.indocyber.dto.RegisterDto;
import com.indocyber.service.AccountService;
import com.indocyber.utility.Dropdown;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/account")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @InitBinder
    public void initBinder(WebDataBinder dataBinder) {

        StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true); // True: trim to null

        dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
    }

    @PostMapping("/registerAccount")
    public String registerAccount(@Valid @ModelAttribute("account") RegisterDto dto,
                                  BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            System.out.println("sampe sini");
            bindingResult.getAllErrors().forEach(System.out::println);
            return "register-page";
        }

        if(dto.getRole().equals("Buyer")){
            accountService.registerBuyer(dto);
        } else if (dto.getRole().equals("Seller")) {
            accountService.registerSeller(dto);
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

}
