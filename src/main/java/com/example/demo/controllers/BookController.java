package com.example.demo.controllers;

import com.example.demo.forms.BookForm;
import com.example.demo.models.Author;
import com.example.demo.models.Book;
import com.example.demo.services.*;
import com.example.demo.viewmodels.BookViewModel;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Controller
@RequestMapping("/admin/books/")
public class BookController {

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
        BookViewModel form = new BookViewModel();
        model.addAttribute("form", form);

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

        return "books/create";
    }

    @PostMapping("create")
    public String handleCreate(
            @Valid @ModelAttribute("form") BookViewModel form,
            @PathVariable("file") MultipartFile file,
            BindingResult result) throws Exception {

        if (file.isEmpty()) {
            result.rejectValue(
                    "imagePath",
                    "Image missing",
                    "Please choose an image");
        }
        // return and show error
        if (result.hasErrors()) {
            return "/books/create";
        }
        // save file and get file name
        String fileName = uploadService.save(file, "books");
        // update path to form object
        form.setImagePath(fileName);
        // save to db
        bookService.create(form);
        return "redirect:/admin/books/";
    }

    @GetMapping("update/{id}")
    public String updatePage(
            @PathVariable("id") long id,
            Model model) {
        Book entity = bookService.findById(id);
        BookForm form = bookService.mapToForm(entity);
        model.addAttribute("form", form);
        return "books/update";
    }

    @PostMapping("update/{id}")
    public String handleUpdate(
            @PathVariable("id") long id,
            @ModelAttribute("form") BookForm form,
            @PathVariable("file") MultipartFile file,
            BindingResult result) throws IOException {

        if (!form.getId().equals(id)) {
            result.rejectValue(
                    "id",
                    "id error",
                    "Id doesn't match");
        }

        if (result.hasErrors()) {
            return "books/update";
        }

        if (!file.isEmpty()) {
            String fileName = uploadService.save(file, "books");
            // update path to form object
            form.setImagePath(fileName);
        }

        bookService.update(form);

        return "redirect:/admin/books/";
    }

    @GetMapping("delete/{id}")
    public String handleDelete(
            @PathVariable("id") long id) {
        bookService.delete(id);
        return "redirect:/admin/books/";
    }
}

