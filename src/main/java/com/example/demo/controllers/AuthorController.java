package com.example.demo.controllers;

import com.example.demo.exceptions.AppValidationException;
import com.example.demo.models.Author;
import com.example.demo.services.AuthorService;
import com.example.demo.viewmodels.AuthorViewModel;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin/authors/")
public class AuthorController {
    private static final String INDEX_TEMPLATE = "author/index";
    private static final String ACTION_TEMPLATE = "author/create";
    private static final String INDEX_URL = "/admin/authors/";

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
                .toList();

        model.addAttribute("list", list);
        return INDEX_TEMPLATE;
    }

    @GetMapping("create")
    public String createPage(Model model) {
        AuthorViewModel form = new AuthorViewModel();
        model.addAttribute("form", form);
        return ACTION_TEMPLATE;
    }

    @GetMapping("update/{id}")
    public String updatePage(
            @PathVariable("id") long id,
            Model model) {
        Author entity = authorService.findById(id);
        AuthorViewModel form = authorService.mapToViewModel(entity);
        model.addAttribute("form", form);
        return ACTION_TEMPLATE;
    }

    @PostMapping("save")
    public String handleCreate(
            @Valid @ModelAttribute("form") AuthorViewModel form,
            BindingResult result) throws AppValidationException {

        if (result.hasErrors()) {
            return "/author/create";
        }

        authorService.save(form);
        return "redirect:" + INDEX_URL;
    }

    @GetMapping("delete/{id}")
    public String handleDelete(
            @PathVariable("id") long id) {
        authorService.delete(id);
        return "redirect:" + INDEX_URL;
    }

}
