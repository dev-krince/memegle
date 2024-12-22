package com.krince.memegle.domain.image.repository;

import com.krince.memegle.domain.image.entity.Bookmark;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {

    Optional<Bookmark> findByImageIdAndUserId(Long imageId, Long userId);
}
