package com.indocyber.controller.mvc;

import com.indocyber.entity.Account;
import com.indocyber.service.AccountService;
import com.indocyber.service.CartMerchandiseService;
import com.indocyber.service.CartService;
import com.indocyber.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequestMapping("/myCart")
public class MyCartController {

    @Autowired
    private CartMerchandiseService cartMerchandiseService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private TransactionService transactionService;


    @GetMapping("/index")
    public String viewMyCartPage(Model model) {
        model.addAttribute("merchandiseList",
                cartMerchandiseService.getCartListByUsername(accountService.getAccount().getUsername()));
//                (SecurityContextHolder.getContext().getAuthentication().getName()));
        model.addAttribute("account", accountService.getAccount());
        return "my-cart-page";
    }

    @PostMapping("/remove")
    public String removeMerchandiseFromCart(@RequestParam(name = "id") int id) {
        try {
            cartMerchandiseService.deleteCartMerchandise(id);
        } catch (Exception e) {
            System.out.println(e);
        }
        return "redirect:/myCart/index";
    }

    @GetMapping("/purchase")
    public String purchasingAllCart() {
        Account account = accountService.getAccount();
//        System.out.println(cartService.countTotalPriceIncludeShipment());
        if(account.getBalance().compareTo(transactionService.countTotalPriceIncludeShipment()) >= 0) {
            transactionService.putCartToTransaction();
            return "redirect:/myCart/index";
        }else {
            return "redirect:/myCart/index?insufficient";
        }
//        switch (account.getBalance().compareTo(cartService.countTotalPriceIncludeShipment())) {
//            case 0:
//            case 1: {
//                cartService.purchasingMerchandiseOnCart();
//                return "redirect:/myCart/index";
//            }
//            case -1:
//            default: {
//                return "redirect:/myCart/index?insufficient";
//            }
//        }
    }
}
