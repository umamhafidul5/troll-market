package com.indocyber.controller.mvc;

import com.indocyber.dto.RegisterAdminDto;
import com.indocyber.dto.RegisterDto;
import com.indocyber.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AccountService accountService;

    @InitBinder
    public void initBinder(WebDataBinder dataBinder) {

        StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true); // True: trim to null

        dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
    }

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
