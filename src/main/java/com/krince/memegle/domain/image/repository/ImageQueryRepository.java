package com.krince.memegle.domain.image.repository;

import com.krince.memegle.domain.image.dto.ViewImageDto;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ImageQueryRepository {

    Optional<ViewImageDto> findViewImageDtoById(Long imageId);

    List<ViewImageDto> findAllViewImageDtoByImageCategory(String imageCategoryValue, Pageable pageable);

    List<ViewImageDto> findAllViewImageDtoByUserIdBookmark(Long userId);

    List<ViewImageDto> findAllViewImageDtoByTagName(String tagName, Pageable pageable);
}
