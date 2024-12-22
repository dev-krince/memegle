package com.krince.memegle.domain.category.service;

import com.krince.memegle.domain.category.entity.ImageCategory;
import com.krince.memegle.domain.category.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class CategoryDomainServiceImpl implements CategoryDomainService {

    private final CategoryRepository categoryRepository;

    @Override
    public void validateUsedImageCategory(String imageCategory) {
        boolean isExistImageCategory = categoryRepository.existsByImageCategoryValue(imageCategory);

        if (!isExistImageCategory) {
            throw new NoSuchElementException("존재하지 않는 카테고리입니다.");
        }
    }

    @Override
    public ImageCategory getImageCategory(String imageCategory) {
        return categoryRepository.findByImageCategoryValue(imageCategory).orElseThrow(() -> new NoSuchElementException("존재하지 않는 카테고리입니다."));
    }
}
