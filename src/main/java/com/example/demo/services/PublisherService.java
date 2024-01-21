package com.example.demo.services;

import com.example.demo.models.Genres;
import com.example.demo.models.Publisher;
import com.example.demo.viewmodels.GenresViewModel;
import com.example.demo.viewmodels.PublisherViewModel;

import java.util.List;

public interface PublisherService {
    List<Publisher> listAll();
    Publisher create(PublisherViewModel form);
    Publisher findById(long id);
    Publisher update(PublisherViewModel form);
    void delete(long id);
    PublisherViewModel mapToViewModel(Publisher entity);
}
