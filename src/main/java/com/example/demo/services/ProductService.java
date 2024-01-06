package com.example.demo.services;

import com.example.demo.models.Product;
import com.example.demo.repositories.ProductRepository;
import com.example.demo.dto.ProductDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<ProductDTO> listAll() {
        var list = productRepository.findAll();
        return list.stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    private ProductDTO mapToDTO(Product product) {
        return ProductDTO.builder()
                .id(product.getId())
                .name(product.getName())
                .price(product.getPrice())
                .createdOn(product.getCreatedOn())
                .updatedOn(product.getUpdatedOn())
                .build();
    }

}
