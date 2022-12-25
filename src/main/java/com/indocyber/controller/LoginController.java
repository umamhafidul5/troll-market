package com.indocyber.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/my-login")
public class LoginController {

    @GetMapping("/showMyLoginPage")
    public String formLogin() {
        return "login";
    }
}
