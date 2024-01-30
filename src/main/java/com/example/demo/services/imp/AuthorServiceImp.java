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
public class AuthorServiceImp
        extends GenericServiceImp<Author, AuthorViewModel>
        implements AuthorService {

    @Autowired
    public AuthorServiceImp(AuthorRepository authorRepository) {
        super(authorRepository);
    }

    @Override
    protected Author newEntity() {
        return new Author();
    }

    @Override
    protected void loadFormIntoEntity(
            Author entity,
            AuthorViewModel form) {
        entity.setName(form.getName());
        entity.setDescription(form.getDescription());

        final boolean isImagePathHaveChange = form.getImagePath() != null && !form.getImagePath().isEmpty();
        if (isImagePathHaveChange) {
            entity.setImagePath(form.getImagePath());
        }
    }

    @Override
    public AuthorViewModel mapToViewModel(Author entity) {
        return AuthorViewModel.builder()
                .id(entity.getId())
                .name(entity.getName())
                .description(entity.getDescription())
                .imagePath(entity.getImagePath())
                .books(entity.getBooks())
                .createdOn(entity.getCreatedOn())
                .updatedOn(entity.getUpdatedOn())
                .build();
    }

}
