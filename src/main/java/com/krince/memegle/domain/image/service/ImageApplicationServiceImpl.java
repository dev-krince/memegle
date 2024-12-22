package com.krince.memegle.domain.image.service;

import com.krince.memegle.domain.category.entity.ImageCategory;
import com.krince.memegle.domain.category.service.CategoryDomainService;
import com.krince.memegle.domain.image.dto.ImageIdDto;
import com.krince.memegle.domain.image.dto.RegistImageDto;
import com.krince.memegle.domain.image.dto.ViewImageDto;
import com.krince.memegle.domain.image.entity.Bookmark;
import com.krince.memegle.domain.image.entity.Image;
import com.krince.memegle.domain.tag.entity.Tag;
import com.krince.memegle.domain.tag.entity.TagMap;
import com.krince.memegle.domain.tag.service.TagDomainService;
import com.krince.memegle.global.aws.S3Service;
import com.krince.memegle.global.dto.PageableDto;
import com.krince.memegle.global.security.CustomUserDetails;
import com.krince.memegle.util.PageUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ImageApplicationServiceImpl implements ImageApplicationService {

    private final ImageDomainService imageDomainService;
    private final TagDomainService tagDomainService;
    private final CategoryDomainService categoryDomainService;
    private final S3Service s3Service;

    @Override
    public ViewImageDto getImage(Long imageId) {
        return imageDomainService.getViewImageDtoFromId(imageId);
    }

    @Override
    public List<ViewImageDto> getCategoryImages(String imageCategoryValue, PageableDto pageableDto) {
        Pageable pageable = PageUtil.createSortedPageable(pageableDto);

        return imageDomainService.getPageableImagesFromImageCategory(imageCategoryValue, pageable);
    }

    @Override
    @Transactional
    public String registMemeImage(RegistImageDto registImageDto) throws IOException {
        String tags = registImageDto.getTags();
        String delimiter = registImageDto.getDelimiter();
        categoryDomainService.validateUsedImageCategory(registImageDto.getImageCategoryValue());
        ImageCategory imageCategory = categoryDomainService.getImageCategory(registImageDto.getImageCategoryValue());
        String[] tagNameList = tags.split(delimiter);
        List<Tag> registedTagList = tagDomainService.getRegistedTagList(tagNameList);
        List<Tag> notRegistedTagList = tagDomainService.getNotRegistedTagList(tagNameList);
        List<Tag> savedTagList = tagDomainService.registTagList(notRegistedTagList);
        registedTagList.addAll(savedTagList);

        String memeImageUrl = s3Service.uploadFile(registImageDto.getMemeImageFile());
        Image image = Image.of(memeImageUrl, imageCategory.getId());
        Image savedImage = imageDomainService.registImage(image);

        List<TagMap> tagMapList = registedTagList.stream().map(tag -> TagMap.of(tag, savedImage)).toList();
        tagDomainService.registTagMapList(tagMapList);

        return memeImageUrl;
    }

    @Override
    @Transactional
    public void changeBookmarkState(ImageIdDto imageIdDto, CustomUserDetails userDetails) {
        Long imageId = imageIdDto.getImageId();
        Long userId = userDetails.getId();
        Optional<Bookmark> findBookmark = imageDomainService.getBookmark(imageId, userId);

        if (findBookmark.isPresent()) {
            Bookmark bookmark = findBookmark.get();
            bookmark.changeIsBookmark();

            return;
        }

        imageDomainService.validateExistsImage(imageId);

        Bookmark bookmark = Bookmark.of(userId, imageId);

        imageDomainService.registBookmark(bookmark);
    }

    @Override
    public List<ViewImageDto> getBookmarkImages(CustomUserDetails userDetails) {
        return imageDomainService.findAllViewImageDtoByUserIdBookmark(userDetails.getId());
    }

    @Override
    public List<ViewImageDto> getTagImages(String tagName, PageableDto pageableDto) {
        Pageable pageable = PageUtil.createSortedPageable(pageableDto);

        return imageDomainService.getPageableImagesFromImageCategory(tagName, pageable);
    }
}
