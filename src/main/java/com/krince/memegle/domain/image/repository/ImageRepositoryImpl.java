package com.krince.memegle.domain.image.repository;

import com.krince.memegle.domain.category.entity.QImageCategory;
import com.krince.memegle.domain.image.dto.ViewImageDto;
import com.krince.memegle.domain.image.entity.QImage;
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

    private ConstructorExpression<ViewImageDto> generateViewImageDtoProjection() {
        return Projections.constructor(ViewImageDto.class,
                image.id,
                image.imageUrl,
                imageCategory.imageCategoryValue,
                image.createdAt,
                image.modifiedAt);
    }

}
