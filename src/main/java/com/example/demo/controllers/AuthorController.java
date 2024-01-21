package com.example.demo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/authors/")
public class AuthorController {

    public AuthorController() {
    }

    @GetMapping()
    public String index() {
        return "author/index";
    }
}
