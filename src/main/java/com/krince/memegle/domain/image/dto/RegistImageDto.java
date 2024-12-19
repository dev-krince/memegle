package com.krince.memegle.domain.image.dto;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import static lombok.AccessLevel.*;

@Getter
@Builder
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor(access = PROTECTED)
public class RegistImageDto {

    private MultipartFile memeImageFile;
    private String imageCategoryValue;
    private String tags;
    private String delimiter;

    public static RegistImageDto of(MultipartFile memeImageFile, String imageCategoryValue, String tags, String delimiter) {
        return RegistImageDto.builder()
                .memeImageFile(memeImageFile)
                .imageCategoryValue(imageCategoryValue)
                .tags(tags)
                .delimiter(delimiter)
                .build();
    }
}
