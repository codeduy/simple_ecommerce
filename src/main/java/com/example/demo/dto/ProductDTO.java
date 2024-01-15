package com.example.demo.dto;

import com.example.demo.models.Product;
import jakarta.validation.constraints.DecimalMin;
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
public class ProductDTO {
    private Long id;
    @NotEmpty(message = "This field is required")
    private String name;
    @NotNull(message = "This field is required")
    private Double price ;
    private LocalDateTime createdOn;
    private LocalDateTime updatedOn;
}
