package com.indocyber.controller.mvc;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/home")
public class HomeController {

    @RequestMapping("/index")
    public String index() {

        return "home-page";
    }

//    @GetMapping("/access-denied")
//    public String showAccessDenied() {
//
//        return "access-denied";
//    }
}
