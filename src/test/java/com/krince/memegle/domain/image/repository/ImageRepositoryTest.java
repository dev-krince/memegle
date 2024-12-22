package com.krince.memegle.domain.image.repository;

import com.krince.memegle.domain.category.entity.ImageCategory;
import com.krince.memegle.domain.category.repository.CategoryRepository;
import com.krince.memegle.domain.image.dto.ViewImageDto;
import com.krince.memegle.domain.image.entity.Bookmark;
import com.krince.memegle.domain.image.entity.Image;
import com.krince.memegle.domain.image.entity.RegistStatus;
import com.krince.memegle.domain.tag.entity.TagMap;
import com.krince.memegle.domain.tag.repository.TagMapRepository;
import com.krince.memegle.domain.tag.repository.TagRepository;
import com.krince.memegle.domain.user.entity.User;
import com.krince.memegle.domain.user.repository.UserRepository;
import com.krince.memegle.global.constant.Role;
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

    @Autowired
    private BookmarkRepository bookmarkRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TagRepository tagRepository;

    @Autowired
    private TagMapRepository tagMapRepository;

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

        @Tag("develop")
        @Nested
        @DisplayName("회원이 즐겨찾기한 이미지 리스트 조회")
        class FindAllViewImageDtoByUserIdBookmark {

            @Nested
            @DisplayName("성공")
            class Success {

                @Test
                @DisplayName("즐겨찾기한 개수만금 조회가 된다.")
                void success() {
                    //given
                    User user = User.builder().loginId("testid").password("testPassword").nickname("testNickname").role(Role.ROLE_USER).build();
                    User saveUser = userRepository.save(user);
                    Long userId = saveUser.getId();

                    String imageCategory = "MUDO";
                    int imageCount = 5;
                    saveImages(imageCount, imageCategory);

                    Bookmark bookmark1 = Bookmark.of(userId, 1L);
                    Bookmark bookmark2 = Bookmark.of(userId, 2L);
                    Bookmark bookmark3 = Bookmark.of(userId, 3L);
                    bookmarkRepository.saveAll(List.of(bookmark1, bookmark2, bookmark3));

                    //when
                    List<ViewImageDto> findViewImageDtoList = imageRepository.findAllViewImageDtoByUserIdBookmark(userId);

                    //then
                    assertThat(findViewImageDtoList.size()).isEqualTo(3);
                }

                @Test
                @DisplayName("즐겨찾기한 이미지가 없다면 빈 리스트를 반환한다.")
                void notFoundImage() {
                    //given
                    User user = User.builder().loginId("testid").password("testPassword").nickname("testNickname").role(Role.ROLE_USER).build();
                    User saveUser = userRepository.save(user);
                    Long userId = saveUser.getId();

                    String imageCategory = "MUDO";
                    int imageCount = 5;
                    saveImages(imageCount, imageCategory);

                    //when
                    List<ViewImageDto> findViewImageDtoList = imageRepository.findAllViewImageDtoByUserIdBookmark(userId);

                    //then
                    assertThat(findViewImageDtoList.size()).isEqualTo(0);
                }
            }

            @Nested
            @DisplayName("실패")
            class Fail {
            }
        }
    }

    @Tag("develop")
    @Tag("target")
    @Nested
    @DisplayName("태그 이름으로 이미지 조회")
    class FindAllViewImageDtoByTagName {

        @Nested
        @DisplayName("성공")
        class Success {

            @Test
            @DisplayName("테그 이름이 존재하면 pageable 설정대로 반환한다.")
            void success() {
                //given
                String imageCategory = "MUDO";
                int imageCount = 5;
                saveImages(imageCount, imageCategory);

                com.krince.memegle.domain.tag.entity.Tag tag1 = com.krince.memegle.domain.tag.entity.Tag.of("테스트1");
                com.krince.memegle.domain.tag.entity.Tag tag2 = com.krince.memegle.domain.tag.entity.Tag.of("테스트2");
                com.krince.memegle.domain.tag.entity.Tag savedTag1 = tagRepository.save(tag1);
                com.krince.memegle.domain.tag.entity.Tag savedTag2 = tagRepository.save(tag2);

                TagMap tagMap1 = TagMap.builder().imageId(1L).tagId(savedTag1.getId()).build();
                TagMap tagMap2 = TagMap.builder().imageId(2L).tagId(savedTag1.getId()).build();
                TagMap tagMap3 = TagMap.builder().imageId(3L).tagId(savedTag1.getId()).build();
                TagMap tagMap4 = TagMap.builder().imageId(4L).tagId(savedTag1.getId()).build();
                TagMap tagMap5 = TagMap.builder().imageId(5L).tagId(savedTag2.getId()).build();
                tagMapRepository.saveAll(List.of(tagMap1, tagMap2, tagMap3, tagMap4, tagMap5));

                //when
                List<ViewImageDto> findViewImageDtoList = imageRepository.findAllViewImageDtoByTagName("테스트1", PageRequest.of(0, 10, Sort.by("createdAt").descending()));

                //then
                assertThat(findViewImageDtoList.size()).isEqualTo(4);
            }

            @Test
            @DisplayName("테그 이름이 존재하지 않으면 빈 List 를 반환한다.")
            void notFoundImage() {
                //given
                String imageCategory = "MUDO";
                int imageCount = 5;
                saveImages(imageCount, imageCategory);

                com.krince.memegle.domain.tag.entity.Tag tag1 = com.krince.memegle.domain.tag.entity.Tag.of("테스트1");
                com.krince.memegle.domain.tag.entity.Tag tag2 = com.krince.memegle.domain.tag.entity.Tag.of("테스트2");
                com.krince.memegle.domain.tag.entity.Tag savedTag1 = tagRepository.save(tag1);
                com.krince.memegle.domain.tag.entity.Tag savedTag2 = tagRepository.save(tag2);

                TagMap tagMap1 = TagMap.builder().imageId(1L).tagId(savedTag1.getId()).build();
                TagMap tagMap2 = TagMap.builder().imageId(2L).tagId(savedTag1.getId()).build();
                TagMap tagMap3 = TagMap.builder().imageId(3L).tagId(savedTag1.getId()).build();
                TagMap tagMap4 = TagMap.builder().imageId(4L).tagId(savedTag1.getId()).build();
                TagMap tagMap5 = TagMap.builder().imageId(5L).tagId(savedTag2.getId()).build();
                tagMapRepository.saveAll(List.of(tagMap1, tagMap2, tagMap3, tagMap4, tagMap5));

                //when
                List<ViewImageDto> findViewImageDtoList = imageRepository.findAllViewImageDtoByTagName("테스트3", PageRequest.of(0, 10, Sort.by("createdAt").descending()));

                //then
                assertThat(findViewImageDtoList.size()).isEqualTo(0);
            }
        }

        @Nested
        @DisplayName("실패")
        class Fail {
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