package com.example.demo.models;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "books")
public class Book extends BaseEntity{
    private String name;
    private Double price;
    private String imagePath;
    private Boolean isActive = true;
    private String description;
    @ManyToOne
    @JoinColumn(name = "author_id", nullable = false)
    private Author author;
    @ManyToOne
    @JoinColumn(name = "genres_id", nullable = false)
    private Genres genres;
    @ManyToOne
    @JoinColumn(name = "publisher_id", nullable = false)
    private Publisher publisher;
    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL)
    private Collection<Comment> comments = new ArrayList<>();
    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL)
    private Collection<Mark> marks = new ArrayList<>();
}
