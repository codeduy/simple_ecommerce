package com.example.demo.services;

import com.example.demo.exceptions.AppValidationException;
import com.example.demo.forms.BookForm;
import com.example.demo.models.Book;
import com.example.demo.viewmodels.BookViewModel;

import java.util.List;


public interface BookService {
    List<Book> listAll();
    Book findById(long id);
    void delete(long productId);
    BookViewModel mapToViewModel(Book entity);
    Book save(BookViewModel form) throws AppValidationException;

}
