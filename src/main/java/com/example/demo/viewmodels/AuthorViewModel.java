package com.example.demo.viewmodels;

import com.example.demo.models.Book;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.Collection;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class AuthorViewModel extends BaseViewModel {
    @NotEmpty(message = "Author name is required")
    private String name;
    private Collection<Book> books;
}
