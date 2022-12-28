package com.indocyber.controller.mvc;

import com.indocyber.dto.MerchandiseDto;
import com.indocyber.service.AccountService;
import com.indocyber.service.MerchandiseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/merchandise")
public class MerchandiseController {

    @Autowired
    private MerchandiseService merchandiseService;

    @Autowired
    private AccountService accountService;

    @InitBinder
    public void initBinder(WebDataBinder dataBinder) {

        StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true); // True: trim to null

        dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
    }

    @RequestMapping("/index")
    public String index(Model model) {

        model.addAttribute("merchandiseList",
                merchandiseService.getAllMerchandise());

        model.addAttribute("account", accountService.getAccount());

        return "merchandise-page";
    }

    @GetMapping("/addProduct")
    public String addProduct(Model model) {

        MerchandiseDto dto = new MerchandiseDto();

        model.addAttribute("merchandise", dto);
        model.addAttribute("account", accountService.getAccount());

        return "add-merchandise-page";
    }

    @PostMapping("/saveProduct")
    public String saveProduct(@Valid @ModelAttribute("merchandise") MerchandiseDto dto,
                              BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "add-merchandise-page";
        }

        merchandiseService.saveProduct(dto);

        return "redirect:/merchandise/index";
    }
}
