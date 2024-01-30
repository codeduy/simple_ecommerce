package com.example.demo.controllers;

import com.example.demo.exceptions.AppValidationException;
import com.example.demo.models.Author;
import com.example.demo.services.AuthorService;
import com.example.demo.services.UploadService;
import com.example.demo.viewmodels.AuthorViewModel;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/authors/")
public class AuthorController {
    private static final String INDEX_TEMPLATE = "author/index";
    private static final String ACTION_TEMPLATE = "author/create";
    private static final String INDEX_URL = "/admin/authors/";

    private final AuthorService authorService;
    private final UploadService uploadService;

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
    public String handleSave(
            @RequestParam("file") MultipartFile file,
            @Valid @ModelAttribute("form") AuthorViewModel form,
            BindingResult result)
            throws AppValidationException, IOException {

        final boolean isCreateAction = form.getId() == null;

        if (result.hasErrors()) {
            return ACTION_TEMPLATE;
        }

        final boolean isImageMissing = isCreateAction && file.isEmpty();
        if (isImageMissing) {
            result.rejectValue(
                    "imagePath",
                    "image missing",
                    "Please choose image to upload");
            return ACTION_TEMPLATE;
        }

        final boolean hasFileToUpload = !file.isEmpty();
        if (hasFileToUpload) {
            String fileName = uploadService.save(file, "authors");
            form.setImagePath(fileName);
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
