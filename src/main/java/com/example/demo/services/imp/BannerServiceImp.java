package com.example.demo.services.imp;

import com.example.demo.models.Author;
import com.example.demo.models.Banner;
import com.example.demo.repositories.BannerRepository;
import com.example.demo.services.AuthorService;
import com.example.demo.services.BannerService;
import com.example.demo.viewmodels.AuthorViewModel;
import com.example.demo.viewmodels.BannerViewModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public class BannerServiceImp
        extends GenericServiceImp<Banner, BannerViewModel>
        implements BannerService {

    public BannerServiceImp(BannerRepository bannerRepository) {
        super(bannerRepository);
    }

    @Override
    public BannerViewModel mapToViewModel(Banner entity) {
        return BannerViewModel.builder()
                .id(entity.getId())
                .imagePath(entity.getImagePath())
                .isActive(entity.isActive())
                .orderNumber(entity.getOrderNumber())
                .build();
    }

    @Override
    protected Banner newEntity() {
        return new Banner();
    }

    @Override
    protected void loadFormIntoEntity(Banner entity, BannerViewModel form) {
        entity.setActive(form.isActive());
        entity.setOrderNumber(form.getOrderNumber());
        final boolean hasFileUpload =  form.getImagePath() != null;
        if (hasFileUpload) {
            entity.setImagePath(form.getImagePath());
        }
    }
}
