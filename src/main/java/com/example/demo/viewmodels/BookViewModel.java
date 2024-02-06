package com.example.demo.viewmodels;

import com.example.demo.models.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
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
    @Min(value = 0, message = "Price must be greater than or equal to 0")
    private Double price;
    private String imagePath;
    private Boolean isActive = true;
    private String description;
    @NotNull(message = "Author can't be blank")
    private Long authorId;
    private Author author;
    @NotNull(message = "Genres can't be blank")
    private Long genresId;
    private Genres genres;
    @NotNull(message = "Publisher can't be blank")
    private Long publisherId;
    private Publisher publisher;
    private Collection<Comment> comments;
    private Collection<Mark> marks;
}
