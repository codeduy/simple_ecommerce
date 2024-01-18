package com.example.demo.controllers;

import com.example.demo.forms.BookForm;
import com.example.demo.models.Book;
import com.example.demo.services.UploadService;
import com.example.demo.viewmodels.BookViewModel;
import jakarta.validation.Valid;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import com.example.demo.services.ProductService;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

@Controller
@RequestMapping("admin/products/")
public class BookController {
    private final ProductService productService;
    private final UploadService uploadService;

    @Autowired
    public BookController(
            ProductService productService,
            UploadService uploadService) {
        this.productService = productService;
        this.uploadService = uploadService;
    }

    @GetMapping("")
    public String index(Model model) {
        var list = productService.listAll();
        model.addAttribute("list", list);
        return "books/index";
    }

    @GetMapping("create")
    public String create(Model model) {
        BookForm form = new BookForm();
        model.addAttribute("form", form);
        return "books/create";
    }

    @PostMapping("create")
    public String handleCreate(
            @Valid @ModelAttribute("form") BookForm form,
            @PathVariable("file") MultipartFile file,
            BindingResult result) throws IOException {

        if (file.isEmpty()) {
            result.rejectValue(
                    "imagePath",
                    "Image missing",
                    "Please choose an image");
        }

        if (result.hasErrors()) {
            return "/books/create";
        }
        // sua lai tra ve ten ngau nhien uid
        String fileName = uploadService.save(file, "books");
        // cap nhat ten vao doi tuong form
        productService.create(form);
        return "redirect:/admin/products/";
    }

    @GetMapping("/{productId}/update")
    public String update(
            @PathVariable("productId") long productId,
            Model model) {
        BookViewModel dto = productService.findById(productId);
        model.addAttribute("product", dto);
        return "products/update";
    }
    @PostMapping("/{productId}/update")
    public String handleUpdate(
            @PathVariable("productId") long productId,
            @ModelAttribute("product") BookViewModel product) {

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
