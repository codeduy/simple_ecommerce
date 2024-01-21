package com.example.demo.services;

import com.example.demo.models.Author;
import com.example.demo.models.Genres;
import com.example.demo.viewmodels.AuthorViewModel;
import com.example.demo.viewmodels.GenresViewModel;

import java.util.List;

public interface GenresService {
    List<Genres> listAll();
    Genres create(GenresViewModel form);
    Genres findById(long id);
    Genres update(GenresViewModel form);
    void delete(long id);
    GenresViewModel mapToViewModel(Genres entity);
}
