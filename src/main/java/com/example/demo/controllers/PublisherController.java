package com.example.demo.controllers;

import com.example.demo.exceptions.AppValidationException;
import com.example.demo.services.PublisherService;
import com.example.demo.services.UploadService;
import com.example.demo.viewmodels.PublisherViewModel;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
@RequestMapping("/admin/publishers/")
public class PublisherController {
    public static final String INDEX_URL = "/admin/publishers/";
    private static final String ACTION_TEMPLATE = "publishers/create";
    private static final String INDEX_TEMPLATE = "publishers/index";
    private final PublisherService publisherService;
    private final UploadService uploadService;

    @Autowired
    public PublisherController(PublisherService publisherService, UploadService uploadService) {
        this.publisherService = publisherService;
        this.uploadService = uploadService;
    }

    @GetMapping
    public String index(Model model) {
        var list = publisherService
                .listAll()
                .stream().map(publisherService::mapToViewModel)
                .toList();

        model.addAttribute("list", list);
        return INDEX_TEMPLATE;
    }


    @GetMapping("create")
    public String createPage(Model model) {
        var form = new PublisherViewModel();
        model.addAttribute("form", form);
        return ACTION_TEMPLATE;
    }

    @GetMapping("update/{id}")
    public String updatePage(
            @PathVariable("id") long id,
            Model model) {
        var entity = publisherService.findById(id);
        var form = publisherService.mapToViewModel(entity);
        model.addAttribute("form", form);
        return ACTION_TEMPLATE;
    }

    @PostMapping("save")
    public String handleSave(
            @RequestParam("file") MultipartFile file,
            @Valid @ModelAttribute("form") PublisherViewModel form,
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
            String fileName = uploadService.save(file, "publishers");
            form.setImagePath(fileName);
        }
        publisherService.save(form);
        return "redirect:" + INDEX_URL;
    }

    @GetMapping("delete/{id}")
    public String handleDelete(
            @PathVariable("id") long id) {
        publisherService.delete(id);
        return "redirect:" + INDEX_URL;
    }
}
