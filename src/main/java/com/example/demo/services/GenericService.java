package com.example.demo.services;

import com.example.demo.exceptions.AppValidationException;
import com.example.demo.models.BaseEntity;
import com.example.demo.viewmodels.BaseViewModel;

import java.util.List;

public interface GenericService<Entity extends BaseEntity, ViewModel extends BaseViewModel> {
    List<Entity> listAll();
    Entity findById(long id);
    void delete(long id);
    ViewModel mapToViewModel(Entity entity);
    Entity save(ViewModel form) throws AppValidationException;
}
