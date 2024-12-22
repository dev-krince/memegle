package com.krince.memegle.domain.tag.entity;

import com.krince.memegle.domain.image.entity.Image;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Getter
@Table(name = "tag_maps")
@Builder
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class TagMap {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long tagId;

    @Column(nullable = false)
    private Long imageId;

    public static TagMap of(Tag tag, Image image) {
        return TagMap.builder()
                .imageId(image.getId())
                .tagId(tag.getId())
                .build();
    }
}
