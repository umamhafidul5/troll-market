package com.indocyber.controller.restController;

import com.indocyber.dto.CartMerchandiseDto;
import com.indocyber.entity.Account;
import com.indocyber.entity.Merchandise;
import com.indocyber.service.AccountService;
import com.indocyber.service.CartMerchandiseService;
import com.indocyber.service.MerchandiseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/shop")
public class ShopRestController {

    @Autowired
    private MerchandiseService merchandiseService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private CartMerchandiseService cartMerchandiseService;

    @GetMapping(
            value = {
                    "/getShopIndex",
                    "/getShopIndex/name={name}",
                    "/getShopIndex/category={category}",
                    "/getShopIndex/description={description}",
                    "/getShopIndex/name={name}/category={category}",
                    "/getShopIndex/name={name}/description={description}",
                    "/getShopIndex/category={category}/description={description}",
                    "/getShopIndex/name={name}/category={category}/description={description}",
            })
    public ResponseEntity<List<Merchandise>> getHistoryIndex
            (@PathVariable(value = "name", required = false) String name,
             @PathVariable(value = "category", required = false) String category,
             @PathVariable(value = "description", required = false) String description){

        name = name == null ? "" : name;
        category = category == null ? "" : category;
        description = description == null ? "" : description;

        List<Merchandise> merchandiseList = merchandiseService.searchMerchandises(name, category, description);

        return new ResponseEntity<>(merchandiseList, HttpStatus.OK);
    }

    @PostMapping("/addToCart/id={id}")
    public ResponseEntity<String> addToCart(@Valid @RequestBody CartMerchandiseDto dto,
                                            BindingResult bindingResult,
                                            @PathVariable("id") Integer id) {

        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(bindingResult.getFieldError().getDefaultMessage(), HttpStatus.BAD_REQUEST);
        }

        try {
            dto.setId(id);
            Account buyer = accountService.getAccount();
            cartMerchandiseService.saveCartMerchandise(dto, buyer);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>("Add To Cart Success", HttpStatus.CREATED);
    }

    @GetMapping("/info/id={id}")
    public ResponseEntity<Merchandise> infoMerchandise(@PathVariable("id") Integer id){

        Merchandise merchandise = merchandiseService.findById(id);

        return new ResponseEntity<>(merchandise, HttpStatus.OK);
    }
}
