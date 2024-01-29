package com.example.demo.services.imp;

import com.example.demo.exceptions.AppException;
import com.example.demo.exceptions.AppValidationException;
import com.example.demo.forms.BookForm;
import com.example.demo.models.Publisher;
import com.example.demo.services.AuthorService;
import com.example.demo.services.GenresService;
import com.example.demo.services.PublisherService;
import com.example.demo.viewmodels.BookViewModel;
import com.example.demo.models.Book;
import com.example.demo.repositories.BookRepository;
import com.example.demo.services.BookService;
import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookServiceImp
        extends GenericServiceImp<Book, BookViewModel>
        implements BookService {
    private final BookRepository bookRepository;
    private final AuthorService authorService;
    private final GenresService genresService;
    private final PublisherService publisherService;

    @Autowired
    public BookServiceImp(
            BookRepository bookRepository,
            AuthorService authorService,
            GenresService genresService,
            PublisherService publisherService) {
        super(bookRepository);
        this.bookRepository = bookRepository;
        this.authorService = authorService;
        this.genresService = genresService;
        this.publisherService = publisherService;
    }

    @Override
    protected Book newEntity() {
        return new Book();
    }

    @Override
    protected void loadFormIntoEntity(Book entity, BookViewModel form) {
        entity.setName(form.getName());
        entity.setPrice(form.getPrice());
        entity.setGenres(form.getGenres());
        entity.setPublisher(form.getPublisher());
        entity.setAuthor(form.getAuthor());
        // image path
        boolean isUploadNewImage = form.getImagePath() != null && !form.getImagePath().isEmpty();
        if (isUploadNewImage) {
            entity.setImagePath(form.getImagePath());
        }
    }

//    public List<Book> listAll() {
//        return bookRepository
//                .findAll(Sort.by(Sort.Direction.DESC, "updatedOn"));
//    }

//    public Book findById(long id) {
//        return bookRepository
//                .findById(id)
//                .orElse(null);
//    }

//    public void delete(long id) {
//        bookRepository.deleteById(id);
//    }

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

//    @Override
//    public Book save(BookViewModel form) throws AppValidationException {
//        var entity = new Book();
//        // update case -> check entity exist
//        if (form.getId() != null) {
//            entity = bookRepository.findById(form.getId()).orElseThrow(
//                    () -> new AppValidationException(
//                            "Book is not found",
//                            "id",
//                            "id doesn't exist")
//            );
//        }
//        // check author
//        var author = authorService.findById(form.getAuthorId());
//        if (author == null) {
//            throw new AppValidationException(
//                    "Author is not found",
//                    "authorId",
//                    "authorId doesn't exist"
//            );
//        }
//        entity.setAuthor(author);
//        // check publisher
//        var publisher = publisherService.findById(form.getPublisherId());
//        if (publisher == null) {
//            throw new AppValidationException(
//                    "publisher is not found",
//                    "publisherId",
//                    "publisherId doesn't exist"
//            );
//        }
//        entity.setPublisher(publisher);
//        // check genres
//        var genres = genresService.findById(form.getGenresId());
//        if (genres == null) {
//            throw new AppValidationException(
//                    "genres is not found",
//                    "genresId",
//                    "genresId doesn't exist"
//            );
//        }
//        entity.setGenres(genres);
//        // get input from form
//        setEntity(entity, form);
//        return bookRepository.save(entity);
//    }

    @Override
    public Book save(BookViewModel form)
            throws AppValidationException {
        // check author
        var author = authorService.findById(form.getAuthorId());
        if (author == null) {
            throw new AppValidationException(
                    "Author is not found",
                    "authorId",
                    "authorId doesn't exist"
            );
        }
        // check publisher
        var publisher = publisherService.findById(form.getPublisherId());
        if (publisher == null) {
            throw new AppValidationException(
                    "publisher is not found",
                    "publisherId",
                    "publisherId doesn't exist"
            );
        }
        // check genres
        var genres = genresService.findById(form.getGenresId());
        if (genres == null) {
            throw new AppValidationException(
                    "genres is not found",
                    "genresId",
                    "genresId doesn't exist"
            );
        }
        form.setAuthor(author);
        form.setPublisher(publisher);
        form.setGenres(genres);
        return super.save(form);
    }
}
