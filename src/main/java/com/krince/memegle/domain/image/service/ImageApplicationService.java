package com.krince.memegle.domain.image.service;

import com.krince.memegle.domain.image.dto.ImageIdDto;
import com.krince.memegle.domain.image.dto.RegistImageDto;
import com.krince.memegle.domain.image.dto.ViewImageDto;
import com.krince.memegle.global.dto.PageableDto;
import com.krince.memegle.global.security.CustomUserDetails;

import java.io.IOException;
import java.util.List;

public interface ImageApplicationService {

    ViewImageDto getImage(Long imageId);

    List<ViewImageDto> getCategoryImages(String imageCategory, PageableDto pageableDto);

    String registMemeImage(RegistImageDto registImageDto) throws IOException;

    void changeBookmarkState(ImageIdDto imageIdDto, CustomUserDetails userDetails);

    List<ViewImageDto> getBookmarkImages(CustomUserDetails userDetails);

    List<ViewImageDto> getTagImages(String tagName, PageableDto pageableDto);
}
