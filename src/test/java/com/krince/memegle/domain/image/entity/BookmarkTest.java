package com.krince.memegle.domain.image.entity;

import org.junit.jupiter.api.*;

import static org.assertj.core.api.Assertions.*;

@Tag("test")
@Tag("unitTest")
@DisplayName("북마크 엔티티(Bookmark)")
class BookmarkTest {

    @Tag("develop")
    @Nested
    @DisplayName("북마크 객체 생성")
    class Constructor {

        @Nested
        @DisplayName("성공")
        class Success {

            @Test
            @DisplayName("북마크를 생성하면 기본 isBookmark 의 상태는 true 상태이다")
            void success() {
                //given
                Bookmark bookmark = Bookmark.of(1L, 1L);

                //when, then
                assertThat(bookmark.isBookmark()).isTrue();
            }
        }
    }

    @Tag("develop")
    @Nested
    @DisplayName("즐겨찾기 상태 변경")
    class changeIsBookmark {

        @Nested
        @DisplayName("성공")
        class Success {

            @Test
            @DisplayName("처음 생성한 true 상태의 북마크를 변경하면 false 상태가 된다.")
            void success() {
                //given
                Bookmark bookmark = Bookmark.of(1L, 1L);

                //when
                bookmark.changeIsBookmark();

                //then
                assertThat(bookmark.isBookmark()).isFalse();
            }
        }
    }
}