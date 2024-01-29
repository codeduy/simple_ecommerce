package com.example.demo.viewmodels;

import com.example.demo.models.Book;
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
    private String name;
    private Collection<Book> books;
}
