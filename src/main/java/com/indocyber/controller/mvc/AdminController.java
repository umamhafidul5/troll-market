package com.indocyber.controller.mvc;

import com.indocyber.dto.RegisterAdminDto;
import com.indocyber.dto.RegisterDto;
import com.indocyber.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AccountService accountService;

    @GetMapping("/index")
    public String index(Model model) {

        RegisterDto dto = new RegisterDto();
        dto.setRole("Admin");

        model.addAttribute("account", dto);

        return "admin-page";
    }

    @PostMapping("/registerAdmin")
    public String registerAccount(@Valid @ModelAttribute("account") RegisterAdminDto dto,
                                  BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            System.out.println("sampe sini");
            bindingResult.getAllErrors().forEach(System.out::println);
            return "admin-page";
        }
        accountService.registerAdmin(dto);

        return "redirect:/admin/index";
    }
}
