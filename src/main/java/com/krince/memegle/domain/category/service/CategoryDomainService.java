package com.krince.memegle.domain.category.service;

import com.krince.memegle.domain.category.entity.ImageCategory;
import org.springframework.stereotype.Service;

@Service
public interface CategoryDomainService {
    void validateUsedImageCategory(String imageCategory);

    ImageCategory getImageCategory(String imageCategory);
}
