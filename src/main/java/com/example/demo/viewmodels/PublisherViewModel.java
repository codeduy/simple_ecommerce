package com.example.demo.viewmodels;

import com.example.demo.models.Book;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.Collection;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class PublisherViewModel extends BaseViewModel {
    private String name;
    private Collection<Book> books;
}
