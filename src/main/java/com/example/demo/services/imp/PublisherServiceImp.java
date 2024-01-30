package com.example.demo.services.imp;

import com.example.demo.exceptions.AppValidationException;
import com.example.demo.models.Publisher;
import com.example.demo.repositories.PublisherRepository;
import com.example.demo.services.PublisherService;
import com.example.demo.viewmodels.PublisherViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PublisherServiceImp
        extends GenericServiceImp<Publisher, PublisherViewModel>
        implements PublisherService {
    @Autowired
    public PublisherServiceImp(PublisherRepository publisherRepository) {
        super(publisherRepository);
    }

    @Override
    protected Publisher newEntity() {
        return new Publisher();
    }

    @Override
    protected void loadFormIntoEntity(
            Publisher entity,
            PublisherViewModel form) {
        entity.setId(form.getId());
        entity.setName(form.getName());
        entity.setDescription(form.getDescription());

        final boolean isImagePathHaveChange = form.getImagePath() != null && !form.getImagePath().isEmpty();
        if (isImagePathHaveChange) {
            entity.setImagePath(form.getImagePath());
        }
    }

    @Override
    public PublisherViewModel mapToViewModel(Publisher entity) {
        return PublisherViewModel.builder()
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
