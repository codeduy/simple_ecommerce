package com.example.demo.viewmodels;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookViewModel {
    private Long id;
    private String name;
    private Double price ;
    private LocalDateTime createdOn;
    private LocalDateTime updatedOn;
}
