package com.example.demo.viewmodels;

import com.example.demo.models.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.Collection;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class BookViewModel extends BaseViewModel {
    @NotEmpty(message = "Book name is required")
    private String name;
    private Double price;
    private String imagePath;
    private Boolean isActive = true;
    private Long authorId;
    private Author author;
    private Long genresId;
    private Genres genres;
    private Long publisherId;
    private Publisher publisher;
    private Collection<Comment> comments;
    private Collection<Mark> marks;
}
