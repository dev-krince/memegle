package com.krince.memegle.domain.category.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;

import static lombok.AccessLevel.*;

@Getter
@Builder
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor(access = PROTECTED)
public class CategoryDto {

    @Schema(description = "카테고리 고유번호")
    private Long id;

    @Schema(description = "카테고리 한글명")
    private String categoryName;

    @Schema(description = "카테고리 코드")
    private String imageCategoryValue;

    @Schema(description = "대표 이미지 url")
    private String titleImageUrl;

    @Schema(description = "해당 카테고리에 마지막으로 밈 이미지가 등록된 시간")
    private LocalDateTime lastMemeImageRegistTime;
}
