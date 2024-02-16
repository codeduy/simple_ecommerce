package com.example.demo.controllers;

import com.example.demo.services.BannerService;
import com.example.demo.services.BookService;
import com.example.demo.services.GenresService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequiredArgsConstructor
public class HomeController {
    private static final String INDEX_TEMPLATE = "home/index";
    private static final String PRODUCT_DETAIL_TEMPLATE = "home/product_detail";
    private static final String CART_TEMPLATE = "home/cart";

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

    @GetMapping("/products/{id}")
    public String productDetail(
            @PathVariable("id") Long id,
            Model model) {
        var item = bookService.findById(id);
        model.addAttribute("item", item);

        return PRODUCT_DETAIL_TEMPLATE;
    }

    @GetMapping("/cart")
    public String cartPage() {
        return CART_TEMPLATE;
    }
}
