package com.example.demo.services;

import com.example.demo.models.Author;
import com.example.demo.models.Book;
import com.example.demo.repositories.AuthorRepository;
import com.example.demo.viewmodels.AuthorViewModel;

import java.util.List;

public interface AuthorService
        extends GenericService<Author, AuthorViewModel>{

}
