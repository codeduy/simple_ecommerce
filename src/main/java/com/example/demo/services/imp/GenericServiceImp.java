package com.example.demo.services.imp;

import com.example.demo.exceptions.AppValidationException;
import com.example.demo.models.BaseEntity;
import com.example.demo.services.GenericService;
import com.example.demo.viewmodels.BaseViewModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public abstract class GenericServiceImp<Entity extends BaseEntity, ViewModel extends BaseViewModel>
        implements GenericService<Entity, ViewModel> {

    private final JpaRepository<Entity, Long> repository;

    public GenericServiceImp(JpaRepository<Entity, Long> repository) {
        this.repository = repository;
    }

    protected abstract Entity newEntity();
    protected abstract void loadFormIntoEntity(Entity entity, ViewModel form);

    @Override
    public List<Entity> listAll() {
        return repository.findAll();
    }

    @Override
    public Entity findById(long id) {
        var optional = repository.findById(id);
        return optional.orElse(null);
    }

    @Override
    public void delete(long id) {
        repository.deleteById(id);
    }

    @Override
    public Entity save(ViewModel form)
            throws AppValidationException {
        Entity entity = newEntity();

        if (form.getId() != null) {
            entity = repository
                    .findById(form.getId())
                    .orElseThrow(() ->
                            new AppValidationException(
                                    "id doesn't exist",
                                    "id",
                                    "id doesn't exist"));
        }
        loadFormIntoEntity(entity, form);
        return repository.save(entity);
    }

}
