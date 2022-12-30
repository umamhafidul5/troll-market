package com.indocyber.controller.restController;

import com.indocyber.dto.CartMerchandiseDto;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class RestShop {

    @PostMapping(value = "/addToCart2")
    public String fetchPost(@RequestBody CartMerchandiseDto cartMerchandiseDto) {
        System.out.println("controller" + cartMerchandiseDto);
        return "ok";
    }
}
