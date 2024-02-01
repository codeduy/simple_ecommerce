package com.example.demo.repositories;

import com.example.demo.models.Banner;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BannerRepository
        extends JpaRepository<Banner, Long> {
}
