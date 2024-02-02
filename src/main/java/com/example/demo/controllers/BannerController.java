package com.example.demo.controllers;


import com.example.demo.exceptions.AppValidationException;
import com.example.demo.services.*;
import com.example.demo.viewmodels.BannerViewModel;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@Controller
@RequestMapping("/admin/banners/")
public class BannerController {
    private static final String ACTION_TEMPLATE = "banners/action";
    private static final String INDEX_TEMPLATE = "banners/index";
    private static final String INDEX_URL = "/admin/banners/";


    private final BannerService bannerService;
    private final BookService bookService;
    private final UploadService uploadService;
    private final AuthorService authorService;
    private final GenresService genresService;
    private final PublisherService publisherService;

    @GetMapping
    public String index(Model model) {
        var list = bannerService
                .listAll()
                .stream().map(bannerService::mapToViewModel)
                .toList();
        model.addAttribute("list", list);
        return INDEX_TEMPLATE;
    }

    @GetMapping("create")
    public String create(Model model) {
        var form = new BannerViewModel();
        model.addAttribute("form", form);
        return ACTION_TEMPLATE;
    }

    @GetMapping("update/{id}")
    public String updatePage(
            @PathVariable("id") long id,
            Model model) {
        var entity = bannerService.findById(id);
        var form = bannerService.mapToViewModel(entity);
        model.addAttribute("form", form);
        return ACTION_TEMPLATE;
    }

    @PostMapping("save")
    public String handleSave(
            @RequestParam("file") MultipartFile file,
            @Valid @ModelAttribute("form") BannerViewModel form,
            BindingResult result)
            throws Exception {

        var isCreateAction = form.getId() == null;

        if (result.hasErrors()) {
            return ACTION_TEMPLATE;
        }

        // validate for add action
        boolean isImageMissing = isCreateAction && file.isEmpty();
        if (isImageMissing) {
            result.rejectValue(
                    "imagePath",
                    "Image missing",
                    "Please choose an image");
            return ACTION_TEMPLATE;
        }

        try {
            boolean hasImageToUpload = !file.isEmpty();
            if (hasImageToUpload) {
                String fileName = uploadService.save(file, "banners");
                form.setImagePath(fileName);
            }
            bannerService.save(form);
            return "redirect:" + INDEX_URL;
        } catch (AppValidationException exception) {
            result.rejectValue(
                    exception.getField(),
                    exception.getErrorCode(),
                    exception.getMessage());
            return ACTION_TEMPLATE;
        }
    }
//
//    @GetMapping("delete/{id}")
//    public String handleDelete(
//            @PathVariable("id") long id) {
//        bookService.delete(id);
//        return "redirect:" + INDEX_URL;
//    }
}
