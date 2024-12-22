package com.krince.memegle.domain.image.controller;

import com.krince.memegle.domain.image.dto.ImageIdDto;
import com.krince.memegle.domain.image.dto.ViewImageDto;
import com.krince.memegle.global.dto.PageableDto;
import com.krince.memegle.global.response.ResponseCode;
import com.krince.memegle.global.response.SuccessResponse;
import com.krince.memegle.global.security.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

import static org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE;

@Tag(name = "이미지", description = "이미지 관련 API")
public abstract class BaseImageController {

    @PostMapping(consumes = MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "밈 이미지 등록 요청", description = "밈 이미지를 등록 요청합니다.")
    @ApiResponse(description = "밈 이미지 등록 요청 성공", responseCode = "20400", content = @Content)
    @ApiResponse(description = "올바르지 않은 양식", responseCode = "40001", ref = "#/components/responses/40001")
    @ApiResponse(description = "인증 정보 불일치", responseCode = "40100", ref = "#/components/responses/40100")
    @ApiResponse(description = "존재하지 않는 리소스", responseCode = "40401", ref = "#/components/responses/40401")
    @ApiResponse(description = "알 수 없는 에러", responseCode = "50000", ref = "#/components/responses/50000")
    public abstract ResponseEntity<ResponseCode> registMemeImage(
            @RequestParam String imageCategory,
            @RequestPart MultipartFile memeImage,
            @RequestPart @NotBlank String tags,
            @RequestPart @NotNull String delimiterFile,
            @Parameter(hidden = true) @AuthenticationPrincipal CustomUserDetails userDetails
    ) throws IOException;

    @GetMapping("/{imageId}")
    @Operation(summary = "밈 이미지 조회", description = "밈 이미지를 조회합니다.")
    @ApiResponse(description = "밈 이미지 조회 성공", responseCode = "20000")
    @ApiResponse(description = "올바르지 않은 양식", responseCode = "40001", ref = "#/components/responses/40001")
    @ApiResponse(description = "존재하지 않는 리소스", responseCode = "40401", ref = "#/components/responses/40401")
    @ApiResponse(description = "알 수 없는 에러", responseCode = "50000", ref = "#/components/responses/50000")
    public abstract ResponseEntity<SuccessResponse<ViewImageDto>> getImage(
            @PathVariable Long imageId,
            @Parameter(hidden = true) @AuthenticationPrincipal CustomUserDetails userDetails
    );

    @GetMapping("/bookmark")
    @Operation(summary = "즐겨찾기 이미지 리스트 조회(미구현 api)", description = "즐겨찾기에 추가한 이미지 리스트를 조회합니다.")
    @ApiResponse(description = "즐겨찾기 이미지 리스트 조회 성공", responseCode = "20000")
    @ApiResponse(description = "인증 정보 불일치", responseCode = "40100", ref = "#/components/responses/40100")
    @ApiResponse(description = "유효하지 않은 토큰", responseCode = "40101", ref = "#/components/responses/40101")
    @ApiResponse(description = "토큰 정보 누락", responseCode = "40103", ref = "#/components/responses/40103")
    @ApiResponse(description = "만료된 토큰", responseCode = "40104", ref = "#/components/responses/40104")
    @ApiResponse(description = "존재하지 않는 리소스", responseCode = "40401", ref = "#/components/responses/40401")
    @ApiResponse(description = "알 수 없는 에러", responseCode = "50000", ref = "#/components/responses/50000")
    public abstract ResponseEntity<SuccessResponse<ViewImageDto>> getBookmarkImages(@Parameter(hidden = true) @AuthenticationPrincipal CustomUserDetails userDetails);

    @PostMapping("/bookmark")
    @Operation(summary = "이미지 즐겨찾기 추가 및 삭제", description = "선택한 이미지의 즐겨찾기 상태를 변경합니다.")
    @ApiResponse(description = "이미지 즐겨찾기 추가 및 삭제 성공", responseCode = "20400", content = @Content)
    @ApiResponse(description = "올바르지 않은 요청", responseCode = "40000", ref = "#/components/responses/40000")
    @ApiResponse(description = "올바르지 않은 양식", responseCode = "40001", ref = "#/components/responses/40001")
    @ApiResponse(description = "유효하지 않은 토큰", responseCode = "40101", ref = "#/components/responses/40101")
    @ApiResponse(description = "존재하지 않는 리소스", responseCode = "40401", ref = "#/components/responses/40401")
    @ApiResponse(description = "알 수 없는 에러", responseCode = "50000", ref = "#/components/responses/50000")
    public abstract ResponseEntity<ResponseCode> changeBookmarkState(
            @RequestBody @Valid ImageIdDto imageIdDto,
            @Parameter(hidden = true) @AuthenticationPrincipal CustomUserDetails userDetails
    );

    @GetMapping("/category")
    @Operation(summary = "카테고리 이미지 리스트 조회", description = "선택한 카테고리의 이미지 리스트를 조회합니다.")
    @ApiResponse(description = "카테고리 이미지 리스트 조회 성공", responseCode = "20000")
    @ApiResponse(description = "올바르지 않은 요청", responseCode = "40000", ref = "#/components/responses/40000")
    @ApiResponse(description = "올바르지 않은 양식", responseCode = "40001", ref = "#/components/responses/40001")
    @ApiResponse(description = "알 수 없는 에러", responseCode = "50000", ref = "#/components/responses/50000")
    public abstract ResponseEntity<SuccessResponse<List<ViewImageDto>>> getCategoryImages(
            @RequestParam String imageCategory,
            @ModelAttribute @Valid PageableDto pageableDto,
            @Parameter(hidden = true) @AuthenticationPrincipal CustomUserDetails userDetails
    );

    @GetMapping("/tag")
    @Operation(summary = "태그 이미지 리스트 조회(미구현 api)", description = "선택한 태그의 이미지 리스트를 조회합니다.")
    @ApiResponse(description = "태그 이미지 리스트 조회 성공", responseCode = "20000")
    @ApiResponse(description = "올바르지 않은 요청", responseCode = "40000", ref = "#/components/responses/40000")
    @ApiResponse(description = "올바르지 않은 양식", responseCode = "40001", ref = "#/components/responses/40001")
    @ApiResponse(description = "알 수 없는 에러", responseCode = "50000", ref = "#/components/responses/50000")
    public abstract ResponseEntity<SuccessResponse<List<ViewImageDto>>> getTagImages(
            @RequestParam @NotBlank @Valid String tagName,
            @ModelAttribute @Valid PageableDto pageableDto,
            @Parameter(hidden = true) @AuthenticationPrincipal CustomUserDetails userDetails
    );
}
