package com.krince.memegle.domain.image.service;

import com.krince.memegle.domain.image.dto.ViewImageDto;
import com.krince.memegle.domain.image.entity.Image;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ImageDomainService {

    ViewImageDto getViewImageDtoFromId(Long imageId);

    List<ViewImageDto> getPageableImagesFromImageCategory(String imageCategory, Pageable pageable);

    Image registImage(Image image);
}
