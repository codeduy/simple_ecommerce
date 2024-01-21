package com.example.demo.viewmodels;

import com.example.demo.models.Book;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.Collection;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthorViewModel {
    private Long id;
    @NotEmpty(message = "Author name is required")
    private String name;
    private Collection<Book> books;
    private LocalDateTime createdOn;
    private LocalDateTime updatedOn;
}
