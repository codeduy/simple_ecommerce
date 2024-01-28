package com.example.demo.controllers;

import com.example.demo.exceptions.AppValidationException;
import com.example.demo.models.Book;
import com.example.demo.services.*;
import com.example.demo.viewmodels.BookViewModel;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Controller
@RequestMapping("/admin/books/")
public class BookController {
    private final String ACTION_TEMPLATE = "books/create";
    private static final String CREATE_TITLE = "Create new books to sell";
    private static final String UPDATE_TITLE = "Update your book info";


    private final BookService bookService;
    private final UploadService uploadService;
    private final AuthorService authorService;
    private final GenresService genresService;
    private final PublisherService publisherService;

    @GetMapping
    public String index(Model model) {
        List<BookViewModel> list = bookService
                .listAll()
                .stream().map(bookService::mapToViewModel)
                .collect(Collectors.toList());

        model.addAttribute("list", list);
        return "books/index";
    }

    @GetMapping("create")
    public String create(Model model) {
        // set data for view
        setMessageForDetailView(model, true);

        BookViewModel form = new BookViewModel();
        model.addAttribute("form", form);
        setDataForSelect(model);
        return ACTION_TEMPLATE;
    }

    @GetMapping("update/{id}")
    public String updatePage(
            @PathVariable("id") long id,
            Model model) {
        setMessageForDetailView(model, false);

        Book entity = bookService.findById(id);
        BookViewModel form = bookService.mapToViewModel(entity);
        model.addAttribute("form", form);
        setDataForSelect(model);
        return ACTION_TEMPLATE;
    }

    @PostMapping("save")
    public String handleSave(
            @Valid @ModelAttribute("form") BookViewModel form,
            @PathVariable("file") MultipartFile file,
            BindingResult result,
            Model model) throws Exception {

        var isCreateAction = form.getId() == null;
        setMessageForDetailView(model, isCreateAction);
        setDataForSelect(model);

        // return and show error
        if (result.hasErrors()) {
            return ACTION_TEMPLATE;
        }

        // validate for add action
        if (isCreateAction && file.isEmpty()) {
            result.rejectValue(
                    "imagePath",
                    "Image missing",
                    "Please choose an image");
            return ACTION_TEMPLATE;
        }

        try {
            if (!file.isEmpty()) {
                String fileName = uploadService.save(file, "books");
                form.setImagePath(fileName);
            }
            bookService.save(form);
        } catch ( AppValidationException exception) {
            result.rejectValue(
                    exception.getField(),
                    exception.getErrorCode(),
                    exception.getMessage());
            return ACTION_TEMPLATE;
        }

        return "redirect:/admin/books/";
    }
    @GetMapping("delete/{id}")
    public String handleDelete(
            @PathVariable("id") long id) {
        bookService.delete(id);
        return "redirect:/admin/books/";
    }

    private void setMessageForDetailView(Model model, boolean isCreateAction) {
        if (isCreateAction) {
            model.addAttribute("TITLE", CREATE_TITLE);
        } else {
            model.addAttribute("TITLE", UPDATE_TITLE);
        }
    }

    private void setDataForSelect(Model model) {
        var authors = authorService.listAll()
                .stream()
                .map(authorService::mapToViewModel)
                .toList();
        model.addAttribute("authors", authors);

        var genres = genresService.listAll()
                .stream()
                .map(genresService::mapToViewModel)
                .toList();
        model.addAttribute("genres", genres);

        var publishers = publisherService.listAll()
                .stream()
                .map(publisherService::mapToViewModel)
                .toList();
        model.addAttribute("publishers", publishers);
    }
}

