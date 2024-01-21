package com.example.demo.services.imp;

import com.example.demo.models.Publisher;
import com.example.demo.repositories.PublisherRepository;
import com.example.demo.services.PublisherService;
import com.example.demo.viewmodels.PublisherViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PublisherServiceImp implements PublisherService {

    private final PublisherRepository publisherRepository;
    @Autowired
    public PublisherServiceImp(PublisherRepository publisherRepository) {
        this.publisherRepository = publisherRepository;
    }

    @Override
    public List<Publisher> listAll() {
        return publisherRepository
                .findAll(Sort.by(Sort.Direction.DESC, "updatedOn"));
    }

    @Override
    public Publisher create(PublisherViewModel form) {
        var entity = new Publisher();
        entity.setName(form.getName());
        return publisherRepository.save(entity);
    }

    @Override
    public Publisher findById(long id) {
        var optional = publisherRepository.findById(id);
        if (optional.isEmpty()) {
            return null;
        }
        return optional.get();
    }

    @Override
    public Publisher update(PublisherViewModel form) {
        var optional = publisherRepository.findById(form.getId());
        if (optional.isEmpty()) {
            return null;
        }
        var entity = optional.get();
        entity.setName(form.getName());
        return publisherRepository.save(entity);
    }

    @Override
    public void delete(long id) {
        publisherRepository.deleteById(id);
    }

    @Override
    public PublisherViewModel mapToViewModel(Publisher entity) {
        return PublisherViewModel.builder()
                .id(entity.getId())
                .name(entity.getName())
                .books(entity.getBooks())
                .createdOn(entity.getCreatedOn())
                .updatedOn(entity.getUpdatedOn())
                .build();
    }
}
