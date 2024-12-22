package com.krince.memegle.domain.image.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import static lombok.AccessLevel.*;

@Getter
@Builder
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor(access = PROTECTED)
public class ImageIdDto {

    @Schema(title = "밈 이미지 고유번호", description = "밈 이미지 고유번호", example = "1")
    @NotNull(message = "비어있거나 null 일 수 없습니다.")
    @Min(value = 1, message = "1보다 큰 숫자여야합니다.")
    private Long imageId;
}
