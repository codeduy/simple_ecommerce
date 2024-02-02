package com.example.demo.controllers;

import com.example.demo.services.BannerService;
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
    private static final String INDEX_TEMPLATE = "home/index";


    private final BookService bookService;
    private final GenresService genresService;
    private final BannerService bannerService;

    @GetMapping("/")
    public String index(Model model) {
        var list = bookService.listAll();
        model.addAttribute("list", list);
        var genres = genresService.listAll();
        model.addAttribute("genres", genres);
        var banners = bannerService.listAll();
        model.addAttribute("banners", banners);
        return INDEX_TEMPLATE;
    }

}
