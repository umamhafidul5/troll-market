package com.indocyber.controller.mvc;

import com.indocyber.repository.CartMerchandiseRepository;
import com.indocyber.service.AccountService;
import com.indocyber.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/myCart")
public class MyCartController {

    CartService cartService;
    CartMerchandiseRepository cartMerchandiseRepository;

    @Autowired
    private AccountService accountService;

    @Autowired
    public MyCartController(CartService cartService, CartMerchandiseRepository cartMerchandiseRepository) {
        this.cartService = cartService;
        this.cartMerchandiseRepository = cartMerchandiseRepository;
    }

    @GetMapping("/index")
    public String viewMyCartPage(Model model) {
//        System.out.println(SecurityContextHolder.getContext().getAuthentication().getName());
        model.addAttribute("merchandiseList", cartMerchandiseRepository.getCartListByUsername(SecurityContextHolder.getContext().getAuthentication().getName()));
        model.addAttribute("account", accountService.getAccount());
        return "my-cart-page";
    }

    @PostMapping("/remove")
    public String removeMerchandiseFromCart(@RequestParam(name = "id") int id) {
        try {
            cartMerchandiseRepository.deleteById(id);
        } catch (Exception e) {
            System.out.println(e);
        }
        return "redirect:/myCart/index";
    }
}
