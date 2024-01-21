package com.example.demo.services.imp;

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
public class GenresServiceImp implements GenresService {

    private final GenresRepository genresRepository;
    @Autowired
    public GenresServiceImp(GenresRepository genresRepository) {
        this.genresRepository = genresRepository;
    }

    @Override
    public List<Genres> listAll() {
        return genresRepository
                .findAll(Sort.by(Sort.Direction.DESC, "updatedOn"));
    }

    @Override
    public Genres create(GenresViewModel form) {
        Genres entity = new Genres();
        entity.setName(form.getName());
        return genresRepository.save(entity);
    }

    @Override
    public Genres findById(long id) {
        var optional = genresRepository.findById(id);
        if (optional.isEmpty()) {
            return null;
        }
        return  optional.get();
    }

    @Override
    public Genres update(GenresViewModel form) {
        var optional = genresRepository.findById(form.getId());
        if (optional.isEmpty()) {
            return  null;
        }

        var entity = optional.get();
        entity.setName(form.getName());
        return genresRepository.save(entity);
    }

    @Override
    public void delete(long id) {
        genresRepository.deleteById(id);
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
