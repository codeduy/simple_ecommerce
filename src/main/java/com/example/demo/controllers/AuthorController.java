package com.example.demo.controllers;

import com.example.demo.forms.BookForm;
import com.example.demo.services.AuthorService;
import com.example.demo.viewmodels.AuthorViewModel;
import com.example.demo.viewmodels.BookViewModel;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/admin/authors/")
public class AuthorController {

    private final AuthorService authorService;

    @Autowired
    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @GetMapping
    public String index(Model model) {
        List<AuthorViewModel> list = authorService
                .listAll()
                .stream().map(authorService::mapToViewModel)
                .collect(Collectors.toList());

        model.addAttribute("list", list);
        return "author/index";
    }

    @GetMapping("create")
    public String createPage(Model model) {
        AuthorViewModel form = new AuthorViewModel();
        model.addAttribute("form", form);
        return "author/create";
    }

    @PostMapping("create")
    public String handleCreate(
            @Valid @ModelAttribute("form") AuthorViewModel form,
            BindingResult result) {

        if (result.hasErrors()) {
            return "/books/create";
        }

        authorService.create(form);
        return "redirect:/admin/authors/";
    }
}
