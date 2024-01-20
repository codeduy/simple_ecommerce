package com.example.demo.services;

import com.example.demo.forms.BookForm;
import com.example.demo.models.Book;
import com.example.demo.viewmodels.BookViewModel;

import java.util.List;


public interface ProductService {
    List<BookViewModel> listAll();
    BookViewModel create(BookForm form);
    BookViewModel findById(long productId);
    void update(BookViewModel BookViewModel);
    void delete(long productId);
    BookForm getForUpdate(long id);
}
