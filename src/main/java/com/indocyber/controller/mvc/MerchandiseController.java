package com.indocyber.controller.mvc;

import com.indocyber.service.MerchandiseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/merchandise")
public class MerchandiseController {

    @Autowired
    private MerchandiseService merchandiseService;
    @RequestMapping("/index")
    public String index(Model model) {

        model.addAttribute("merchandiseList",
                merchandiseService.getAllMerchandise());

        return "merchandise-page";
    }
}
