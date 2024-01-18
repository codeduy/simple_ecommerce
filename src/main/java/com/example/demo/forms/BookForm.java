package com.example.demo.forms;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookForm {
    private Long id = -1l;
    @NotBlank(message = "Please enter book name")
    private String name;
    @NotNull(message = "Please enter book price")
    private Double price;
    @NotBlank(message = "Please enter book author")
    private String author;
    private String imagePath;

    private Boolean isActive = true;

}
