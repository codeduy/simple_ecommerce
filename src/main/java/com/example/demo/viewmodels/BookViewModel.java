package com.example.demo.viewmodels;

import com.example.demo.models.*;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
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
public class BookViewModel {
    private Long id;
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
    private LocalDateTime createdOn;
    private LocalDateTime updatedOn;
}
