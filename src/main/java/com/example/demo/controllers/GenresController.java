package com.example.demo.controllers;

import com.example.demo.models.Author;
import com.example.demo.services.GenresService;
import com.example.demo.viewmodels.AuthorViewModel;
import com.example.demo.viewmodels.GenresViewModel;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/admin/genres/")
public class GenresController {

    private final GenresService genresService;
    @Autowired
    public GenresController(GenresService genresService) {
        this.genresService = genresService;
    }

    @GetMapping
    public String index(Model model) {
        List<GenresViewModel> list = genresService
                .listAll()
                .stream().map(genresService::mapToViewModel)
                .collect(Collectors.toList());

        model.addAttribute("list", list);
        return "genres/index";
    }

    @GetMapping("create")
    public String createPage(Model model) {
        GenresViewModel form = new GenresViewModel();
        model.addAttribute("form", form);
        return "genres/create";
    }

    @PostMapping("create")
    public String handleCreate(
            @Valid @ModelAttribute("form") GenresViewModel form,
            BindingResult result) {

        if (result.hasErrors()) {
            return "genres/create";
        }

        genresService.create(form);
        return "redirect:/admin/genres/";
    }

    @GetMapping("update/{id}")
    public String updatePage(
            @PathVariable("id") long id,
            Model model) {
        var entity = genresService.findById(id);
        var form = genresService.mapToViewModel(entity);
        model.addAttribute("form", form);
        return "genres/update";
    }

    @PostMapping("update/{id}")
    public String handleUpdate(
            @PathVariable("id") long id,
            @Valid @ModelAttribute("form") GenresViewModel form,
            BindingResult result) {

        if (!form.getId().equals(id)) {
            result.rejectValue(
                    "id",
                    "id error",
                    "Id doesn't match");
        }

        if (result.hasErrors()) {
            return "genres/update";
        }

        genresService.update(form);

        return "redirect:/admin/genres/";
    }

    @GetMapping("delete/{id}")
    public String handleDelete(
            @PathVariable("id") long id) {
        genresService.delete(id);
        return "redirect:/admin/genres/";
    }
}
