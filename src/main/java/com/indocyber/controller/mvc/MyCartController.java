package com.indocyber.controller.mvc;

import com.indocyber.entity.Account;
import com.indocyber.entity.CartMerchandise;
import com.indocyber.repository.CartMerchandiseRepository;
import com.indocyber.repository.MerchandiseRepository;
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

import java.math.BigDecimal;
import java.util.List;

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

    @GetMapping("/purchase")
    public String purchasingAllCart() {
        Account account = accountService.getAccount();
        System.out.println(cartService.countTotalPriceIncludeShipment());
        switch (account.getBalance().compareTo(cartService.countTotalPriceIncludeShipment())) {
            case 0:
            case 1: {
                cartService.purchasingMerchandiseOnCart();
                return "redirect:/myCart/index";
            }
            case -1:
            default: {
                return "redirect:/myCart/index?insufficient";
            }
        }
    }
}
