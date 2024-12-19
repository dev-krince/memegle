package com.krince.memegle.domain.image.service;

import com.krince.memegle.domain.category.entity.ImageCategory;
import com.krince.memegle.domain.category.repository.CategoryRepository;
import com.krince.memegle.domain.category.repository.fake.FakeCategoryRepository;
import com.krince.memegle.domain.category.service.CategoryDomainService;
import com.krince.memegle.domain.category.service.CategoryDomainServiceImpl;
import com.krince.memegle.domain.image.dto.RegistImageDto;
import com.krince.memegle.domain.image.dto.ViewImageDto;
import com.krince.memegle.domain.image.entity.Image;
import com.krince.memegle.domain.image.repository.fake.FakeImageRepository;
import com.krince.memegle.domain.image.repository.ImageRepository;
import com.krince.memegle.domain.tag.repository.fake.FakeTagMapRepository;
import com.krince.memegle.domain.tag.repository.fake.FakeTagRepository;
import com.krince.memegle.domain.tag.repository.TagMapRepository;
import com.krince.memegle.domain.tag.repository.TagRepository;
import com.krince.memegle.domain.tag.service.TagDomainService;
import com.krince.memegle.domain.tag.service.TagDomainServiceImpl;
import com.krince.memegle.global.aws.fake.FakeAmazonS3;
import com.krince.memegle.global.aws.fake.FakeS3Service;
import com.krince.memegle.global.aws.S3Service;
import com.krince.memegle.global.constant.Criteria;
import com.krince.memegle.global.dto.PageableDto;
import org.junit.jupiter.api.*;
import org.springframework.mock.web.MockMultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@Tag("test")
@Tag("unitTest")
@DisplayName("이미지 어플리케이션 서비스 테스트(ImageApplicationService)")
class ImageApplicationServiceTest {

    static ImageApplicationService imageApplicationService;

    static ImageRepository imageRepository;
    static TagRepository tagRepository;
    static TagMapRepository tagMapRepository;
    static CategoryRepository categoryRepository;

    static ImageDomainService imageDomainService;
    static TagDomainService tagDomainService;
    static CategoryDomainService categoryDomainService;

    static S3Service s3Service;

    @BeforeAll
    static void setUp() {
        imageRepository = new FakeImageRepository();
        tagRepository = new FakeTagRepository();
        tagMapRepository = new FakeTagMapRepository();
        categoryRepository = new FakeCategoryRepository();

        imageDomainService = new ImageDomainServiceImpl(imageRepository);
        tagDomainService = new TagDomainServiceImpl(tagRepository, tagMapRepository);
        categoryDomainService = new CategoryDomainServiceImpl(categoryRepository);

        s3Service = new FakeS3Service(new FakeAmazonS3(), "testBucket", "testRegion");

        imageApplicationService = new ImageApplicationServiceImpl(imageDomainService, tagDomainService, categoryDomainService, s3Service);
    }

    @AfterEach
    void tearDown() {
        imageRepository.deleteAll();
        tagRepository.deleteAll();
        tagMapRepository.deleteAll();
    }

    @Nested
    @DisplayName("이미지 단건 조회")
    class GetImage {

        @Nested
        @DisplayName("성공")
        class GetImageSuccess {

            @Test
            @DisplayName("success")
            void getImageSuccess() {
                //given
                Image image = Image.builder().build();
                imageRepository.save(image);

                //when
                ViewImageDto viewImageDto = imageApplicationService.getImage(1L);

                //then
                assertThat(viewImageDto.getId()).isEqualTo(1L);
            }
        }

        @Nested
        @DisplayName("실패")
        class GetImageFail {

            @Test
            @DisplayName("없는 이미지를 조회하려 하면 예외를 반환한다.")
            void getImageNotFindResource() {
                //given
                Image image = Image.builder().build();
                imageRepository.save(image);

                //when
                Exception exception = assertThrows(Exception.class, () -> imageApplicationService.getImage(100L));

                //then
                assertThat(exception).isInstanceOf(NoSuchElementException.class);
            }
        }
    }

    @Nested
    @DisplayName("카테고리 이미지 리스트 조회")
    class GetImageCategoryImages {

        @Nested
        @DisplayName("성공")
        class GetImageCategoryImagesSuccess {

            @Test
            @DisplayName("success")
            void getCategoryImages() {
                //given
                PageableDto pageableDto = PageableDto.builder()
                        .criteria(Criteria.CREATED_AT)
                        .page(1)
                        .size(1)
                        .build();

                //when
                List<ViewImageDto> images = imageApplicationService.getCategoryImages("MUDO", pageableDto);

                //then
                assertThat(images.size()).isEqualTo(0);
            }
        }
    }

    @Nested
    @DisplayName("밈 이미지 등록 신청 테스트")
    class RegistMemeImage {

        @Nested
        @DisplayName("성공")
        class RegistMemeImageSuccess {

            @Test
            @DisplayName("success")
            void registMemeImage() throws IOException {
                //given
                com.krince.memegle.domain.tag.entity.Tag test1 = com.krince.memegle.domain.tag.entity.Tag.builder()
                        .tagName("test1")
                        .build();
                tagRepository.save(test1);
                com.krince.memegle.domain.tag.entity.Tag test2 = com.krince.memegle.domain.tag.entity.Tag.builder()
                        .tagName("test2")
                        .build();
                tagRepository.save(test2);
                MockMultipartFile mockFile = new MockMultipartFile(
                        "testfile",
                        "test-image.png",
                        "image/png",
                        "imageContent".getBytes()
                );

                categoryRepository.save(ImageCategory.of("MUDO"));

                RegistImageDto registImageDto = RegistImageDto.builder()
                        .memeImageFile(mockFile)
                        .imageCategoryValue("MUDO")
                        .tags("test1 test2")
                        .delimiter(" ")
                        .build();

                //when
                String imageURL = imageApplicationService.registMemeImage(registImageDto);

                //then
                assertThat(imageURL).isEqualTo("https://testImage.com");
            }
        }
    }
}