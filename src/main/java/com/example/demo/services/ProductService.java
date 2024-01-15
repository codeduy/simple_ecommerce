package com.example.demo.services;

import com.example.demo.models.Book;
import com.example.demo.dto.ProductDTO;

import java.util.List;


public interface ProductService {
    List<ProductDTO> listAll();
    Book create(ProductDTO productDTO);
    ProductDTO findById(long productId);
    void update(ProductDTO productDTO);
    void delete(long productId);
}
