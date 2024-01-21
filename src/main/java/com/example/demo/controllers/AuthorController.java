package com.example.demo.controllers;

import com.example.demo.forms.BookForm;
import com.example.demo.models.Author;
import com.example.demo.models.Book;
import com.example.demo.services.AuthorService;
import com.example.demo.viewmodels.AuthorViewModel;
import com.example.demo.viewmodels.BookViewModel;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

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
            return "/author/create";
        }

        authorService.create(form);
        return "redirect:/admin/authors/";
    }

    @GetMapping("update/{id}")
    public String updatePage(
            @PathVariable("id") long id,
            Model model) {
        Author entity = authorService.findById(id);
        AuthorViewModel form = authorService.mapToViewModel(entity);
        model.addAttribute("form", form);
        return "author/update";
    }

    @PostMapping("update/{id}")
    public String handleUpdate(
            @PathVariable("id") long id,
            @Valid @ModelAttribute("form") AuthorViewModel form,
            BindingResult result) {

        if (!form.getId().equals(id)) {
            result.rejectValue(
                    "id",
                    "id error",
                    "Id doesn't match");
        }

        if (result.hasErrors()) {
            return "author/update";
        }

        authorService.update(form);

        return "redirect:/admin/authors/";
    }
    @GetMapping("delete/{id}")
    public String handleDelete(
            @PathVariable("id") long id) {
        authorService.delete(id);
        return "redirect:/admin/authors/";
    }
}
