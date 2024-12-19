package com.krince.memegle.domain.category.repository;

import com.krince.memegle.domain.category.entity.ImageCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<ImageCategory, Long> {

    boolean existsByImageCategoryValue(String imageCategory);

    Optional<ImageCategory> findByImageCategoryValue(String imageCategory);
}
