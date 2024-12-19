package com.krince.memegle.domain.image.repository;

import com.krince.memegle.domain.category.entity.ImageCategory;
import com.krince.memegle.domain.category.repository.CategoryRepository;
import com.krince.memegle.domain.image.dto.ViewImageDto;
import com.krince.memegle.domain.image.entity.Image;
import com.krince.memegle.domain.image.entity.RegistStatus;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;

@Tags({
        @Tag("test"),
        @Tag("integrationTest")
})
@ActiveProfiles("test")
@SpringBootTest
@Transactional
@DisplayName("이미지 리포지토리 테스트(ImageRepository)")
class ImageRepositoryTest {

    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @BeforeEach
    void setUp() {
        categoryRepository.save(ImageCategory.of("ETC"));
        categoryRepository.save(ImageCategory.of("MUDO"));
    }

    @Nested
    @DisplayName("카테고리 이미지 리스트 조회")
    class FindAllByImageImageCategory {

        @Nested
        @DisplayName("성공")
        class FindAllByImageImageCategorySuccess {

            @Test
            @DisplayName("카테고리 이미지 리스트 조회시 해당 카테고리가 아닌 이미지는 조회되지 않는다.")
            void findAllByImageCategoryOrderByCreatedAtDesc() {
                //given
                String imageCategory = "MUDO";
                int imageCount = 5;
                Pageable pageable = PageRequest.of(0, 10, Sort.by("createdAt").descending());

                saveImages(imageCount, imageCategory);
                saveImages(1, "ETC");

                //when
                List<ViewImageDto> images = imageRepository.findAllViewImageDtoByImageCategory(imageCategory, pageable);

                //then
                assertThat(images.size()).isEqualTo(imageCount);
            }
        }
    }

    private void saveImages(int imageCount, String imageCategory) {
        Long imageCategoryId = categoryRepository.findByImageCategoryValue(imageCategory)
                .orElseThrow()
                .getId();

        for (int i = 1; i <= imageCount; i++) {
            imageRepository.save(
                    Image.builder()
                            .imageUrl("https://www.testImage" + i + UUID.randomUUID() + ".com")
                            .registStatus(RegistStatus.REGIST)
                            .imageCategoryId(imageCategoryId)
                            .build()
            );
        }
    }
}