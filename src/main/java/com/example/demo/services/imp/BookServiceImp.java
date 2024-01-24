package com.example.demo.services.imp;

import com.example.demo.exceptions.AppException;
import com.example.demo.forms.BookForm;
import com.example.demo.models.Publisher;
import com.example.demo.services.AuthorService;
import com.example.demo.services.GenresService;
import com.example.demo.services.PublisherService;
import com.example.demo.viewmodels.BookViewModel;
import com.example.demo.models.Book;
import com.example.demo.repositories.BookRepository;
import com.example.demo.services.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookServiceImp implements BookService {
    private final BookRepository bookRepository;
    private final AuthorService authorService;
    private final GenresService genresService;
    private final PublisherService publisherService;

    public List<Book> listAll() {
        return bookRepository
                .findAll(Sort.by(Sort.Direction.DESC, "updatedOn"));
    }

    @Override
    public Book create(BookForm form) {
        Book entity = mapToEntity(form);
        return bookRepository.save(entity);
    }

    @Override
    public Book create(BookViewModel form) throws Exception {
        var entity = new Book();

        var author = authorService.findById(form.getAuthorId());
        if (author == null) {
            throw new AppException("Author is null");
        }
        form.setAuthor(author);

        var genres = genresService.findById(form.getGenresId());
        if (genres == null) {
            throw new AppException("Genres is null");
        }
        form.setGenres(genres);

        var publisher = publisherService.findById(form.getPublisherId());
        if (publisher == null) {
            throw new AppException("Publisher is null");
        }
        form.setPublisher(publisher);

        setEntity(entity, form);

        return bookRepository.save(entity);
    }

    public Book findById(long id) {
        return bookRepository
                .findById(id)
                .orElse(null);
    }

    public Book update(BookForm form) {
        Optional<Book> optional = bookRepository.findById(form.getId());
        if (optional.isEmpty()) {
            // throw ex
            return null;
        }

        Book entity = optional.get();
//        entity.setName(form.getName());
//        entity.setPrice(form.getPrice());
//        entity.setAuthor(form.getAuthor());
//        entity.setImagePath(form.getImagePath());

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
                .isActive(entity.getIsActive())
                .author(entity.getAuthor())
                .authorId(entity.getAuthor().getId())
                .genres(entity.getGenres())
                .genresId(entity.getGenres().getId())
                .publisher(entity.getPublisher())
                .publisherId(entity.getPublisher().getId())
                .comments(entity.getComments())
                .marks(entity.getMarks())
                .createdOn(entity.getCreatedOn())
                .updatedOn(entity.getUpdatedOn())
                .build();
    }

    @Override
    public Book save(BookViewModel form) {
        var isCreateAction = form.getId() == null;
        var entity = new Book();
        if (!isCreateAction) {
            entity = bookRepository.findById(form.getId()).orElseThrow();
        }
        setEntity(entity, form);
        return bookRepository.save(entity);
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

    private void setEntity(Book entity, BookViewModel form) {
        entity.setName(form.getName());
        entity.setPrice(form.getPrice());
        entity.setImagePath(form.getImagePath());
        entity.setAuthor(form.getAuthor());
        entity.setGenres(form.getGenres());
        entity.setPublisher(form.getPublisher());
    }
}
