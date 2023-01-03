package com.indocyber.controller.mvc;

import com.indocyber.dto.CartMerchandiseDto;
import com.indocyber.entity.*;
import com.indocyber.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@Controller
@RequestMapping("/shop")
public class ShopController {

    @Autowired
    private MerchandiseService merchandiseService;

    @Autowired
    private CartService cartService;

    @Autowired
    private CartMerchandiseService cartMerchandiseService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private ShipmentService shipmentService;

    @RequestMapping("/index")
    public String index(
            @RequestParam(name = "name", required = false, defaultValue = "") String name,
            @RequestParam(name = "category", required = false, defaultValue = "") String category,
            @RequestParam(name = "description", required = false, defaultValue = "") String description,
            Model model
    ) {

        model.addAttribute("account", accountService.getAccount());

        model.addAttribute("merchandiseList", merchandiseService
                .searchMerchandises(name, category, description));

        model.addAttribute("shipmentList", shipmentService.getAllShipmentService());

        CartMerchandiseDto dto = new CartMerchandiseDto();
        model.addAttribute("cartMerchandise", dto);

        return "shop-page";
    }

    @RequestMapping("/addToCart")
    public String addToCart(@Valid @ModelAttribute(name = "cartMerchandise") CartMerchandiseDto dto,
                            BindingResult bindingResult,
                            Model model,
                            @RequestParam(name = "name", required = false, defaultValue = "") String name,
                            @RequestParam(name = "category", required = false, defaultValue = "") String category,
                            @RequestParam(name = "description", required = false, defaultValue = "") String description) {

        if (bindingResult.hasErrors()) {

            model.addAttribute("account", accountService.getAccount());
            model.addAttribute("shipmentList", shipmentService.getAllShipment());
            model.addAttribute("merchandiseList", merchandiseService
                    .searchMerchandises(name, category, description));

            return "shop-page";
        }

        Account buyer = accountService.getAccount();
        cartMerchandiseService.saveCartMerchandise(dto, buyer);
        return "redirect:/shop/index";
    }

    @GetMapping("/infoProduct")
    @ResponseBody
    public Merchandise infoProduct(@RequestParam("id") int id){
        Merchandise merchandise = merchandiseService.findById(id);
        return merchandise;
    }

//    coba-coba pake patch
//    @PostMapping(value = "/addToCart2")
//    public String fetchPost(@RequestBody CartMerchandiseDto cartMerchandiseDto) {
//        System.out.println("controller" + cartMerchandiseDto);
//        return "ok";
//    }
}
