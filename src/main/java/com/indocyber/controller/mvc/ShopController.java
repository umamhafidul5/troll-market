package com.indocyber.controller.mvc;

import com.indocyber.service.AccountService;
import com.indocyber.service.MerchandiseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/shop")
public class ShopController {

    @Autowired
    private MerchandiseService merchandiseService;

    @Autowired
    private AccountService accountService;

    @RequestMapping("/index")
    public String index(
            @RequestParam(name = "name", required = false) String name,
            @RequestParam(name = "category", required = false) String category,
            @RequestParam(name = "description", required = false) String description,
            Model model
    ) {

        model.addAttribute("account", accountService.getAccount());

        model.addAttribute("merchandiseList", merchandiseService
                .getAllMerchandiseBy(name, category, description));

        return "shop-page";
    }
}
