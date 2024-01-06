package com.example.demo.dto;

import com.example.demo.models.Product;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ProductDTO {
    private Long id;
    private String name;
    private Double price ;
    private LocalDateTime createdOn;
    private LocalDateTime updatedOn;
}
