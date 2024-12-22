package com.krince.memegle.domain.image.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

import static lombok.AccessLevel.*;

@Entity
@Getter
@Builder
@Table(name = "bookmarks")
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor(access = PROTECTED)
public class Bookmark {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private Long imageId;

    @Builder.Default
    @Column(nullable = false)
    private boolean isBookmark = true;

    @Column(nullable = false)
    @CreatedDate
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    @LastModifiedDate
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime modifiedAt;

    public void changeIsBookmark() {
        this.isBookmark = !this.isBookmark;
    }

    public static Bookmark of(Long userId, Long imageId) {
        return Bookmark.builder()
                .userId(userId)
                .imageId(imageId)
                .build();
    }
}
