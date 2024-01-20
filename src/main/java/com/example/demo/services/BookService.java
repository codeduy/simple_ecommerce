package com.example.demo.services;

import com.example.demo.forms.BookForm;
import com.example.demo.models.Book;
import com.example.demo.viewmodels.BookViewModel;

import java.util.List;


public interface BookService {
    List<Book> listAll();
    Book create(BookForm form);
    Book findById(long id);
    Book update(BookForm form);
    void delete(long productId);
    BookForm mapToForm(Book entity);
    BookViewModel mapToViewModel(Book entity);

}
