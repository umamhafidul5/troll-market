package com.indocyber.controller.mvc;

import com.indocyber.utility.Dropdown;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/my-login")
public class LoginController {

    @GetMapping("/showMyLoginPage")
    public String showMyLoginPage(Model model) {

        List<Dropdown> roleDropdown = Dropdown.getRoleDropdown();

        model.addAttribute("roleDropdown", roleDropdown);

        return "login-page";
    }
}
