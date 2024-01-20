package com.example.demo.services.imp;

import com.example.demo.forms.BookForm;
import com.example.demo.viewmodels.BookViewModel;
import com.example.demo.models.Book;
import com.example.demo.repositories.BookRepository;
import com.example.demo.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookServiceImp implements BookService {
    private final BookRepository bookRepository;
    @Autowired
    public BookServiceImp(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<Book> listAll() {
        return bookRepository
                .findAll(Sort.by(Sort.Direction.DESC, "updatedOn"));
    }

    @Override
    public Book create(BookForm form) {
        Book entity = mapToEntity(form);
        return bookRepository.save(entity);
    }

    public Book findById(long id) {
        var optional = bookRepository.findById(id);
        if (optional.isEmpty()) {
            return null;
        }
        return optional.get();
    }

    public Book update(BookForm form) {
        Optional<Book> optional = bookRepository.findById(form.getId());
        if (optional.isEmpty()) {
            // throw ex
            return null;
        }

        Book entity = optional.get();
        entity.setName(form.getName());
        entity.setPrice(form.getPrice());
        entity.setAuthor(form.getAuthor());
        entity.setImagePath(form.getImagePath());

        return bookRepository.save(entity);
    }

    public void delete(long id) {
        bookRepository.deleteById(id);
    }

    public BookViewModel mapToViewModel(Book entity) {
        return BookViewModel.builder()
                .id(entity.getId())
                .name(entity.getName())
                .price(entity.getPrice())
                .imagePath(entity.getImagePath())
                .createdOn(entity.getCreatedOn())
                .updatedOn(entity.getUpdatedOn())
                .build();
    }

    private Book mapToEntity(BookForm form) {
        return Book.builder()
                .id(form.getId())
                .name(form.getName())
                .price(form.getPrice())
                .imagePath(form.getImagePath())
                .build();
    }

    public BookForm mapToForm(Book entity) {
        return BookForm.builder()
                .id(entity.getId())
                .name(entity.getName())
                .price(entity.getPrice())
                .imagePath(entity.getImagePath())
                .build();
    }
}
