package com.krince.memegle.domain.image.service;

import com.krince.memegle.domain.image.dto.ViewImageDto;
import com.krince.memegle.domain.image.entity.Image;
import com.krince.memegle.domain.image.repository.ImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.NoSuchElementException;

@Repository
@RequiredArgsConstructor
public class ImageDomainServiceImpl implements ImageDomainService {

    private final ImageRepository imageRepository;

    @Override
    public ViewImageDto getViewImageDtoFromId(Long imageId) {
        return imageRepository.findViewImageDtoById(imageId).orElseThrow(NoSuchElementException::new);
    }

    @Override
    public List<ViewImageDto> getPageableImagesFromImageCategory(String imageCategoryValue, Pageable pageable) {
        return imageRepository.findAllViewImageDtoByImageCategory(imageCategoryValue, pageable);
    }

    @Override
    public Image registImage(Image image) {
        return imageRepository.save(image);
    }
}
