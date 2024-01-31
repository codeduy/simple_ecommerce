package com.example.demo.viewmodels;

import com.example.demo.models.Book;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.Collection;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class GenresViewModel extends BaseViewModel {
    @NotEmpty(message = "Genres name is required")
    private String name;
    @NotEmpty(message = "Icon is required")
    private String iconClass;
    @Size(max = 200, message = "Description must be at most 200 characters")
    private String description;
    private Collection<Book> books;
}
