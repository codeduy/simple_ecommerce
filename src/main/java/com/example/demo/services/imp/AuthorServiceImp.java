package com.example.demo.services.imp;

import com.example.demo.models.Author;
import com.example.demo.repositories.AuthorRepository;
import com.example.demo.services.AuthorService;
import com.example.demo.viewmodels.AuthorViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AuthorServiceImp implements AuthorService {

    private final AuthorRepository authorRepository;
    @Autowired
    public AuthorServiceImp(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Override
    public List<Author> listAll() {
        return authorRepository
                .findAll(Sort.by(Sort.Direction.DESC, "updatedOn"));
    }

    @Override
    public AuthorViewModel mapToViewModel(Author entity) {
        return AuthorViewModel.builder()
                .id(entity.getId())
                .name(entity.getName())
                .books(entity.getBooks())
                .createdOn(entity.getCreatedOn())
                .updatedOn(entity.getUpdatedOn())
                .build();
    }

    @Override
    public Author create(AuthorViewModel form) {
        Author entity = new Author();
        entity.setName(form.getName());
        return authorRepository.save(entity);
    }

    @Override
    public Author findById(long id) {
        Optional<Author> optional = authorRepository.findById(id);
        if (optional.isEmpty()) {
            return null;
        }
        return optional.get();
    }

    @Override
    public Author update(AuthorViewModel form) {

        Optional<Author> optional = authorRepository.findById(form.getId());
        if (optional.isEmpty()) {
            return null;
        }

        Author entity = optional.get();
        entity.setName(form.getName());
        return authorRepository.save(entity);
    }

    @Override
    public void delete(long id) {
        authorRepository.deleteById(id);
    }
}
