package com.example.demo.controllers;

import com.example.demo.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    private final BookService productService;
    @Autowired
    public HomeController(BookService productService) {
        this.productService = productService;
    }

    @GetMapping("/")
    public String index(Model model, Authentication authentication) {
        var list = productService.listAll();
        model.addAttribute("list", list);
        return "home/index";
    }

}
