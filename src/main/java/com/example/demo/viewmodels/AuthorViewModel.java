package com.example.demo.viewmodels;

import com.example.demo.models.Book;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
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
public class AuthorViewModel extends BaseViewModel {
    @NotEmpty(message = "Author name is required")
    private String name;
    @Size(max = 200, message = "Max length is 200 characters")
    private String description;
    private String imagePath;
    private Collection<Book> books;
}
