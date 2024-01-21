package com.example.demo.controllers;

import com.example.demo.services.PublisherService;
import com.example.demo.viewmodels.BookViewModel;
import com.example.demo.viewmodels.GenresViewModel;
import com.example.demo.viewmodels.PublisherViewModel;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/admin/publishers/")
public class PublisherController {
    private final PublisherService publisherService;
    @Autowired
    public PublisherController(PublisherService publisherService) {
        this.publisherService = publisherService;
    }

    @GetMapping
    public String index(Model model) {
        var list = publisherService
                .listAll()
                .stream().map(publisherService::mapToViewModel)
                .collect(Collectors.toList());

        model.addAttribute("list", list);
        return "publishers/index";
    }


    @GetMapping("create")
    public String createPage(Model model) {
        var form = new PublisherViewModel();
        model.addAttribute("form", form);
        return "publishers/create";
    }

    @PostMapping("create")
    public String handleCreate(
            @Valid @ModelAttribute("form") PublisherViewModel form,
            BindingResult result) {

        if (result.hasErrors()) {
            return "publishers/create";
        }

        publisherService.create(form);
        return "redirect:/admin/publishers/";
    }

    @GetMapping("update/{id}")
    public String updatePage(
            @PathVariable("id") long id,
            Model model) {
        var entity = publisherService.findById(id);
        var form = publisherService.mapToViewModel(entity);
        model.addAttribute("form", form);
        return "publishers/update";
    }

    @PostMapping("update/{id}")
    public String handleUpdate(
            @PathVariable("id") long id,
            @Valid @ModelAttribute("form") PublisherViewModel form,
            BindingResult result) {

        if (!form.getId().equals(id)) {
            result.rejectValue(
                    "id",
                    "id error",
                    "Id doesn't match");
        }

        if (result.hasErrors()) {
            return "publishers/update";
        }

        publisherService.update(form);

        return "redirect:/admin/publishers/";
    }


    @GetMapping("delete/{id}")
    public String handleDelete(
            @PathVariable("id") long id) {
        publisherService.delete(id);
        return "redirect:/admin/publishers/";
    }
}
