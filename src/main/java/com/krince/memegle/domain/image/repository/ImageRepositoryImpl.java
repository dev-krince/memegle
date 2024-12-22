package com.krince.memegle.domain.image.repository;

import com.krince.memegle.domain.category.entity.QImageCategory;
import com.krince.memegle.domain.image.dto.ViewImageDto;
import com.krince.memegle.domain.image.entity.QBookmark;
import com.krince.memegle.domain.image.entity.QImage;
import com.krince.memegle.domain.tag.entity.QTag;
import com.krince.memegle.domain.tag.entity.QTagMap;
import com.krince.memegle.domain.user.entity.QUser;
import com.querydsl.core.types.ConstructorExpression;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ImageRepositoryImpl implements ImageQueryRepository {

    private final JPAQueryFactory queryFactory;
    private final QImage image = QImage.image;
    private final QImageCategory imageCategory = QImageCategory.imageCategory;
    private final QUser user = QUser.user;
    private final QBookmark bookmark = QBookmark.bookmark;
    private final QTag tag = QTag.tag;
    private final QTagMap tagMap = QTagMap.tagMap;

    @Override
    public Optional<ViewImageDto> findViewImageDtoById(Long imageId) {
        ConstructorExpression<ViewImageDto> viewImageDto = generateViewImageDtoProjection();

        ViewImageDto queryResult = queryFactory
                .select(viewImageDto)
                .from(image)
                .innerJoin(imageCategory).on(image.imageCategoryId.eq(imageCategory.id))
                .where(image.id.eq(imageId))
                .fetchFirst();

        return Optional.ofNullable(queryResult);
    }

    @Override
    public List<ViewImageDto> findAllViewImageDtoByImageCategory(String imageCategoryValue, Pageable pageable) {
        ConstructorExpression<ViewImageDto> viewImageDto = generateViewImageDtoProjection();

        return queryFactory
                .select(viewImageDto)
                .from(image)
                .innerJoin(imageCategory)
                .on(image.imageCategoryId.eq(imageCategory.id)
                        .and(imageCategory.imageCategoryValue.eq(imageCategoryValue))
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
    }

    @Override
    public List<ViewImageDto> findAllViewImageDtoByUserIdBookmark(Long userId) {
        ConstructorExpression<ViewImageDto> viewImageDto = generateViewImageDtoProjection();

        return queryFactory
                .select(viewImageDto)
                .from(image)
                .innerJoin(imageCategory).on(imageCategory.id.eq(image.imageCategoryId))
                .innerJoin(bookmark).on(image.id.eq(bookmark.imageId))
                .innerJoin(user).on(user.id.eq(bookmark.userId))
                .where(user.id.eq(userId))
                .where(bookmark.isBookmark.eq(true))
                .fetch();
    }

    @Override
    public List<ViewImageDto> findAllViewImageDtoByTagName(String tagName, Pageable pageable) {
        ConstructorExpression<ViewImageDto> viewImageDto = generateViewImageDtoProjection();

        return queryFactory
                .select(viewImageDto)
                .from(image)
                .innerJoin(imageCategory).on(imageCategory.id.eq(image.imageCategoryId))
                .innerJoin(tagMap).on(tagMap.imageId.eq(image.id))
                .innerJoin(tag).on(tag.id.eq(tagMap.tagId))
                .where(tag.tagName.eq(tagName))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
    }

    private ConstructorExpression<ViewImageDto> generateViewImageDtoProjection() {
        return Projections.constructor(ViewImageDto.class,
                image.id,
                image.imageUrl,
                imageCategory.imageCategoryValue,
                image.createdAt,
                image.modifiedAt);
    }
}
