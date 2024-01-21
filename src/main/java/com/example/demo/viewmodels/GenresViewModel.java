package com.example.demo.viewmodels;

import com.example.demo.models.Book;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.Collection;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GenresViewModel {
    private Long id;
    private String name;
    private Collection<Book> books;
    private LocalDateTime createdOn;
    private LocalDateTime updatedOn;
}
