package com.example.mobilele.service.impl;

import com.example.mobilele.repository.BrandRepository;
import com.example.mobilele.service.BrandService;
import org.springframework.stereotype.Service;

@Service
public class BrandServiceImpl implements BrandService {

    private BrandRepository brandRepository;

    public BrandServiceImpl(BrandRepository brandRepository) {
        this.brandRepository = brandRepository;
    }
}
