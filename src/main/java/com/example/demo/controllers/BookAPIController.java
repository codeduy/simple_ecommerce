package com.example.demo.controllers;

import com.example.demo.services.BookService;
import com.example.demo.viewmodels.BookViewModel;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/books/")
public class BookAPIController {

    private final BookService bookService;

    @GetMapping("/{id}")
    public ResponseEntity<BookViewModel> getBookById(@PathVariable Long id) {
        var book = bookService.findById(id);
        if (book == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        var bookVm = bookService.mapToViewModel(book);
        return new ResponseEntity<>(bookVm, HttpStatus.OK);

    }
}
