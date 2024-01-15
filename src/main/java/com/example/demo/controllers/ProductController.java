package com.example.demo.controllers;

import com.example.demo.dto.ProductDTO;
import jakarta.validation.Valid;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import com.example.demo.services.ProductService;

@Controller
@RequestMapping("products")
public class ProductController {
    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/")
    public String index(@NotNull Model model) {
        var list = productService.listAll();
        model.addAttribute("list", list);
        return "products/index";
    }

    @GetMapping("/create")
    public String create(Model model) {
        var productDTO = new ProductDTO();
        model.addAttribute("product", productDTO);
        return "products/create";
    }

    @PostMapping("/create")
    public String handleCreate(
            @Valid @ModelAttribute("product") ProductDTO product,
            BindingResult result) {
        if (result.hasErrors()) {
            return "/products/create";
        }
        productService.create(product);
        return "redirect:/products/create";
    }

    @GetMapping("/{productId}/update")
    public String update(
            @PathVariable("productId") long productId,
            Model model) {
        ProductDTO dto = productService.findById(productId);
        model.addAttribute("product", dto);
        return "products/update";
    }
    @PostMapping("/{productId}/update")
    public String handleUpdate(
            @PathVariable("productId") long productId,
            @ModelAttribute("product") ProductDTO product) {

        product.setId(productId);
        productService.update(product);

        return "redirect:/products/";
    }

    @GetMapping("/{productId}/delete")
    public String handleDelete(
            @PathVariable("productId") long productId) {
        productService.delete(productId);
        return "redirect:/products/";
    }
}

