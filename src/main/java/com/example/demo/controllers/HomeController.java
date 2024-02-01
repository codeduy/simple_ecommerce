package com.example.demo.controllers;

import com.example.demo.services.BookService;
import com.example.demo.services.GenresService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final BookService bookService;
    private final GenresService genresService;

    @GetMapping("/")
    public String index(Model model) {
        var list = bookService.listAll();
        model.addAttribute("list", list);
        var genres = genresService.listAll();
        model.addAttribute("genres", genres);
        return "home/index";
    }

}
