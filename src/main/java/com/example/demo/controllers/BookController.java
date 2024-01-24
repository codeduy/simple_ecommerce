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
    private final String ADD_ACTION_TEMPLATE = "books/create";
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
        model.addAttribute("TITLE", CREATE_TITLE);

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

        return ADD_ACTION_TEMPLATE;
    }

    @PostMapping("save")
    public String handleCreate(
            @Valid @ModelAttribute("form") BookViewModel form,
            @PathVariable("file") MultipartFile file,
            BindingResult result,
            Model model) throws Exception {

        var isCreateAction = form.getId() == null;
        setMessageForDetailView(model, isCreateAction);

        // validate for add action
        if (isCreateAction && file.isEmpty()) {
            result.rejectValue(
                    "imagePath",
                    "Image missing",
                    "Please choose an image");
            return ADD_ACTION_TEMPLATE;
        }
        // validate for add and update
        if (form.getAuthorId() == null) {
            result.rejectValue(
                    "authorId",
                    "authorId missing",
                    "Please choose an author");
            return ADD_ACTION_TEMPLATE;
        }

        form.setAuthor(authorService.findById(form.getAuthorId()));
        if (form.getAuthor() == null) {
            result.rejectValue(
                    "authorId",
                    "authorId missing",
                    "Author doesn't exist");
            return ADD_ACTION_TEMPLATE;
        }

        if (form.getGenresId() == null) {
            result.rejectValue(
                    "genresId",
                    "genresId missing",
                    "Please choose an genres");
        }

        form.setGenres( genresService.findById(form.getGenresId()));
        if (form.getGenres() == null) {
            result.rejectValue(
                    "genresId",
                    "genresId missing",
                    "Genres doesn't exist");
            return ADD_ACTION_TEMPLATE;
        }

        if (form.getPublisherId() == null) {
            result.rejectValue(
                    "publisherId",
                    "publisherId missing",
                    "Please choose an publisher");
        }

        form.setPublisher(publisherService.findById(form.getPublisherId()));
        if (form.getPublisher() == null) {
            result.rejectValue(
                    "publisherId",
                    "publisherId missing",
                    "Publisher doesn't exist");
            return ADD_ACTION_TEMPLATE;
        }

        // return and show error
        if (result.hasErrors()) {
            return ADD_ACTION_TEMPLATE;
        }

        if (!file.isEmpty()) {
            // save file and get file name
            String fileName = uploadService.save(file, "books");
            // update path to form object
            form.setImagePath(fileName);
        }

        // save to db
        bookService.save(form);

        return "redirect:/admin/books/";
    }

    @GetMapping("update/{id}")
    public String updatePage(
            @PathVariable("id") long id,
            Model model) {
        Book entity = bookService.findById(id);
        BookViewModel form = bookService.mapToViewModel(entity);
        model.addAttribute("form", form);
        setMessageForDetailView(model, false);
        return ADD_ACTION_TEMPLATE;
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

    private void setMessageForDetailView(Model model, boolean isCreateAction) {
        if (isCreateAction) {
            model.addAttribute("TITLE", CREATE_TITLE);
        } else {
            model.addAttribute("TITLE", UPDATE_TITLE);
        }
    }
}

