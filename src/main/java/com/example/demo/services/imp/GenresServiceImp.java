package com.example.demo.services.imp;

import com.example.demo.exceptions.AppValidationException;
import com.example.demo.models.Author;
import com.example.demo.models.Genres;
import com.example.demo.repositories.GenresRepository;
import com.example.demo.services.GenresService;
import com.example.demo.viewmodels.GenresViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GenresServiceImp extends GenericServiceImp<Genres, GenresViewModel>
        implements GenresService {

    private final GenresRepository genresRepository;
    @Autowired
    public GenresServiceImp(GenresRepository genresRepository) {
        super(genresRepository);
        this.genresRepository = genresRepository;
    }

    @Override
    protected Genres newEntity() {
        return new Genres();
    }

    @Override
    protected void loadFormIntoEntity(Genres entity, GenresViewModel form) {
        entity.setId(form.getId());
        entity.setName(form.getName());
    }

    @Override
    public GenresViewModel mapToViewModel(Genres entity) {
        return GenresViewModel.builder()
                .id(entity.getId())
                .name(entity.getName())
                .books(entity.getBooks())
                .createdOn(entity.getCreatedOn())
                .updatedOn(entity.getUpdatedOn())
                .build();
    }
}
