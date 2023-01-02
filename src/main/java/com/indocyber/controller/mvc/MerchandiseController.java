package com.indocyber.controller.mvc;

import com.indocyber.dto.MerchandiseDto;
import com.indocyber.entity.Merchandise;
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
        model.addAttribute("add", true);

        return "add-merchandise-page";
    }

    @GetMapping("/editProduct")
    public String editProduct(@RequestParam("id") int id, Model model) {

        MerchandiseDto dto = merchandiseService.findProduct(id);

        model.addAttribute("merchandise", dto);
        model.addAttribute("account", accountService.getAccount());
//        model.addAttribute("add", false);

        return "add-merchandise-page";
    }

    @PostMapping("/saveProduct")
    public String saveProduct(@Valid @ModelAttribute("merchandise") MerchandiseDto dto,
                              BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {

            model.addAttribute("account", accountService.getAccount());
            model.addAttribute("add", true);

            return "add-merchandise-page";
        }


        merchandiseService.saveProduct(dto);

        return "redirect:/merchandise/index";
    }

    @GetMapping("/deleteProduct")
    public String deleteProduct(@RequestParam("id") int id){
        merchandiseService.deleteProduct(id);
        return "redirect:/merchandise/index";
    }

    @GetMapping("/discontinue")
    public String discontinue(@RequestParam("id") int id){
        Merchandise merchandise = merchandiseService.findById(id);
        if (!merchandise.getIsDiscontinue()) {
            merchandise.setIsDiscontinue(true);
            merchandiseService.saveMerchandise(merchandise);
        }

        return "redirect:/merchandise/index";
    }

    @GetMapping("/infoProduct")
    @ResponseBody
    public Merchandise infoProduct(@RequestParam("id") int id){
        Merchandise merchandise = merchandiseService.findById(id);
        return merchandise;
    }

}
