package com.example.demo.viewmodels;

import com.example.demo.models.Book;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.Collection;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class PublisherViewModel
        extends BaseViewModel {
    @NotEmpty(message = "required")
    private String name;
    @Size(max = 200, message = "Description must be at most 200 characters")
    private String description;
    private String imagePath;
    private Collection<Book> books;
}
