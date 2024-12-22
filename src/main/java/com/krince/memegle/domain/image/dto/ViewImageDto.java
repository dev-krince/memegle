package com.krince.memegle.domain.image.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class ViewImageDto {

    @Schema(title = "밈 이미지 고유번호", description = "밈 이미지 고유번호", example = "1")
    private Long id;

    @Schema(title = "밈 이미지 url", description = "밈 이미지 url", example = "https://mimegle.s3.ap-northeast-2.amazonaws.com/KakaoTalk_Photo_2024-05-15-17-32-00.jpeg")
    private String imageUrl;

    @Schema(title = "밈 이미지 카테고리", description = "밈 이미지 카테고리", example = "MUDO")
    private String imageCategoryValue;

    @Schema(title = "밈 이미지 생성 일시", description = "밈 이미지 생성 일시", example = "2024-05-19T01:24:05.543105")
    private LocalDateTime createdAt;

    @Schema(title = "밈 이미지 수정 일시", description = "밈 이미지 수정 일시", example = "2024-05-19T01:24:05.543105")
    private LocalDateTime modifiedAt;
}
