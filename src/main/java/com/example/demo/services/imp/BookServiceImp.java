package com.example.demo.services.imp;

import com.example.demo.exceptions.AppValidationException;
import com.example.demo.models.Book;
import com.example.demo.repositories.BookRepository;
import com.example.demo.services.AuthorService;
import com.example.demo.services.BookService;
import com.example.demo.services.GenresService;
import com.example.demo.services.PublisherService;
import com.example.demo.viewmodels.BookViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        entity.setDescription(form.getDescription());
        entity.setGenres(form.getGenres());
        entity.setPublisher(form.getPublisher());
        entity.setAuthor(form.getAuthor());
        // image path
        boolean isUploadNewImage = form.getImagePath() != null && !form.getImagePath().isEmpty();
        if (isUploadNewImage) {
            entity.setImagePath(form.getImagePath());
        }
    }

    public BookViewModel mapToViewModel(Book entity) {
        return BookViewModel.builder()
                .id(entity.getId())
                .name(entity.getName())
                .price(entity.getPrice())
                .imagePath(entity.getImagePath())
                .isActive(entity.getIsActive())
                .description(entity.getDescription())
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
