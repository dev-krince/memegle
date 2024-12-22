package com.krince.memegle.domain.image.service;

import com.krince.memegle.domain.image.dto.ViewImageDto;
import com.krince.memegle.domain.image.entity.Bookmark;
import com.krince.memegle.domain.image.entity.Image;
import com.krince.memegle.domain.image.repository.BookmarkRepository;
import com.krince.memegle.domain.image.repository.ImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ImageDomainServiceImpl implements ImageDomainService {

    private final ImageRepository imageRepository;
    private final BookmarkRepository bookmarkRepository;

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

    @Override
    public Optional<Bookmark> getBookmark(Long imageId, Long userId) {
        return bookmarkRepository.findByImageIdAndUserId(imageId, userId);
    }

    @Override
    public void registBookmark(Bookmark bookmark) {
        bookmarkRepository.save(bookmark);
    }

    @Override
    public void validateExistsImage(Long imageId) {
        imageRepository.findById(imageId).orElseThrow(() -> new NoSuchElementException("이미지가 존재하지 않습니다."));
    }

    @Override
    public List<ViewImageDto> findAllViewImageDtoByUserIdBookmark(Long userId) {
        return imageRepository.findAllViewImageDtoByUserIdBookmark(userId);
    }

    @Override
    public List<ViewImageDto> findPageViewImageDtoByTagName(String tagName, Pageable pageable) {
        return imageRepository.findAllViewImageDtoByTagName(tagName, pageable);
    }
}
