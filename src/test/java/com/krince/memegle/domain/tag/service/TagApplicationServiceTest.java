package com.krince.memegle.domain.tag.service;

import com.krince.memegle.domain.image.entity.Image;
import com.krince.memegle.domain.tag.entity.Tag;
import com.krince.memegle.domain.tag.entity.TagMap;
import com.krince.memegle.domain.tag.repository.fake.FakeTagMapRepository;
import com.krince.memegle.domain.tag.repository.fake.FakeTagRepository;
import com.krince.memegle.domain.tag.repository.TagMapRepository;
import org.junit.jupiter.api.*;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@Tags({
        @org.junit.jupiter.api.Tag("test"),
        @org.junit.jupiter.api.Tag("unitTest")
})
@DisplayName("태그 서비스 테스트(TagService)")
class TagApplicationServiceTest {

    static TagApplicationServiceImpl tagService;

    static FakeTagRepository tagRepository;

    static TagMapRepository tagMapRepository;

    @BeforeAll
    static void setUp() {
        tagRepository = new FakeTagRepository();
        tagMapRepository = new FakeTagMapRepository();
        tagService = new TagApplicationServiceImpl(tagRepository, tagMapRepository);
    }

    @AfterEach
    void tearDown() {
        tagRepository.deleteAll();
        tagMapRepository.deleteAll();
    }

    @Nested
    @DisplayName("태그 이름으로 태그 리스트 조회")
    class GetTags {

        @Nested
        @DisplayName("성공")
        class GetTagsSuccess {

            @Test
            @DisplayName("모두 존재하는 태그들이어야한다.")
            void getTags() {
                //given
                tagRepository.save(Tag.builder().tagName("testTag1").build());
                tagRepository.save(Tag.builder().tagName("testTag2").build());
                String tags = "testTag1 testTag2";
                String delimiter = " ";

                //when
                List<Tag> findTagList = tagService.getTags(tags, delimiter);

                //then
                assertThat(findTagList.size()).isEqualTo(2);
            }
        }
    }

    @Nested
    @DisplayName("태그맵 등록")
    class RegistTagMap {

        @Nested
        @DisplayName("성공")
        class RegistTagMapSuccess {

            @Test
            @DisplayName("태그맵 등록 성공")
            void registTagMap() {
                //given
                Image image = Image.builder().build();
                Tag tag = Tag.builder().build();
                TagMap tagMap = TagMap.builder()
                        .imageId(image.getId())
                        .tagId(tag.getId())
                        .build();

                //when
                TagMap savedTagMap = tagMapRepository.save(tagMap);

                //then
                assertThat(savedTagMap).isInstanceOf(TagMap.class);
            }
        }
    }
}