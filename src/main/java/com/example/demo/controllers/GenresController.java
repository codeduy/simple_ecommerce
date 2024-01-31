package com.example.demo.controllers;

import com.example.demo.exceptions.AppValidationException;
import com.example.demo.services.GenresService;
import com.example.demo.viewmodels.GenresViewModel;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/genres/")
public class GenresController {
    private static final String INDEX_TEMPLATE = "genres/index";
    private static final String ACTION_TEMPLATE = "genres/create";
    private static final String INDEX_URL = "/admin/genres/";

    private final GenresService genresService;

    @GetMapping
    public String index(Model model) {
        List<GenresViewModel> list = genresService
                .listAll()
                .stream().map(genresService::mapToViewModel)
                .toList();

        model.addAttribute("list", list);
        return INDEX_TEMPLATE;
    }

    @GetMapping("create")
    public String createPage(Model model) {
        GenresViewModel form = new GenresViewModel();
        model.addAttribute("form", form);
        return ACTION_TEMPLATE;
    }

    @GetMapping("update/{id}")
    public String updatePage(
            @PathVariable("id") long id,
            Model model) {
        var entity = genresService.findById(id);
        var form = genresService.mapToViewModel(entity);
        model.addAttribute("form", form);
        return ACTION_TEMPLATE;
    }

    @PostMapping("save")
    public String save(
            @Valid @ModelAttribute("form") GenresViewModel form,
            BindingResult result)
            throws AppValidationException {

        if (result.hasErrors()) {
            return ACTION_TEMPLATE;
        }

        genresService.save(form);
        return "redirect:" + INDEX_URL;
    }

    @GetMapping("delete/{id}")
    public String handleDelete(
            @PathVariable("id") long id) {
        genresService.delete(id);
        return "redirect:" + INDEX_URL;
    }
}
