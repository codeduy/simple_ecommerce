package com.example.demo.services.imp;

import com.example.demo.forms.BookForm;
import com.example.demo.viewmodels.BookViewModel;
import com.example.demo.models.Book;
import com.example.demo.repositories.ProductRepository;
import com.example.demo.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImp implements ProductService {
    private final ProductRepository productRepository;
    @Autowired
    public ProductServiceImp(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<BookViewModel> listAll() {
        var list = productRepository
                .findAll(Sort.by(Sort.Direction.DESC, "updatedOn"));
        return list.stream().map(this::mapToViewModel).collect(Collectors.toList());
    }

    @Override
    public BookViewModel create(BookForm form) {
        Book entity = mapToEntity(form);
        Book entityCreated = productRepository.save(entity);
        return mapToViewModel(entityCreated);
    }

    public BookViewModel findById(long productId) {
        var productOptional = productRepository.findById(productId);
        if (productOptional.isEmpty()) {
            return null;
        }
        var product = productOptional.get();
        return mapToViewModel(product);
    }

    public BookForm getForUpdate(long id) {
        var entityOptional = productRepository.findById(id);
        if (entityOptional.isEmpty()) {
            return null;
        }
        var product = entityOptional.get();
        return mapToForm(product);
    }

    public void update(BookViewModel BookViewModel) {
//        Book product = mapToEntity(BookViewModel);
//        productRepository.save(product);
    }

    public void delete(long productId) {
        productRepository.deleteById(productId);
    }

    private BookViewModel mapToViewModel(Book entity) {
        return BookViewModel.builder()
                .id(entity.getId())
                .name(entity.getName())
                .price(entity.getPrice())
                .imagePath(entity.getImagePath())
                .createdOn(entity.getCreatedOn())
                .updatedOn(entity.getUpdatedOn())
                .build();
    }

    private Book mapToEntity(BookForm form) {
        return Book.builder()
                .id(form.getId())
                .name(form.getName())
                .price(form.getPrice())
                .imagePath(form.getImagePath())
                .build();
    }

    private BookForm mapToForm(Book entity) {
        return BookForm.builder()
                .id(entity.getId())
                .name(entity.getName())
                .price(entity.getPrice())
                .imagePath(entity.getImagePath())
                .build();
    }
}
