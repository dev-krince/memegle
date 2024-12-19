package com.krince.memegle.domain.category.repository.fake;

import com.krince.memegle.domain.category.entity.ImageCategory;
import com.krince.memegle.domain.category.repository.CategoryRepository;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

public class FakeCategoryRepository implements CategoryRepository {

    private Map<Long, ImageCategory> store = new HashMap<>();
    private Long sequence = 0L;

    @Override
    public boolean existsByImageCategoryValue(String imageCategoryValue) {
        List<ImageCategory> findImageCategoryList = store.values()
                .stream()
                .filter(imageCategory -> imageCategory.getImageCategoryValue().equals(imageCategoryValue))
                .toList();

        return !findImageCategoryList.isEmpty();
    }

    @Override
    public Optional<ImageCategory> findByImageCategoryValue(String imageCategoryValue) {
        return store.values()
                .stream()
                .filter(imageCategory -> imageCategory.getImageCategoryValue().equals(imageCategoryValue))
                .findFirst();
    }

    @Override
    public void flush() {

    }

    @Override
    public <S extends ImageCategory> S saveAndFlush(S entity) {
        return null;
    }

    @Override
    public <S extends ImageCategory> List<S> saveAllAndFlush(Iterable<S> entities) {
        return List.of();
    }

    @Override
    public void deleteAllInBatch(Iterable<ImageCategory> entities) {

    }

    @Override
    public void deleteAllByIdInBatch(Iterable<Long> longs) {

    }

    @Override
    public void deleteAllInBatch() {

    }

    @Override
    public ImageCategory getOne(Long aLong) {
        return null;
    }

    @Override
    public ImageCategory getById(Long aLong) {
        return null;
    }

    @Override
    public ImageCategory getReferenceById(Long aLong) {
        return null;
    }

    @Override
    public <S extends ImageCategory> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends ImageCategory> List<S> findAll(Example<S> example) {
        return List.of();
    }

    @Override
    public <S extends ImageCategory> List<S> findAll(Example<S> example, Sort sort) {
        return List.of();
    }

    @Override
    public <S extends ImageCategory> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends ImageCategory> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends ImageCategory> boolean exists(Example<S> example) {
        return false;
    }

    @Override
    public <S extends ImageCategory, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return null;
    }

    @Override
    public <S extends ImageCategory> S save(S entity) {
        ImageCategory imageCategory = ImageCategory.builder()
                .id(++sequence)
                .imageCategoryValue(entity.getImageCategoryValue())
                .createdAt(LocalDateTime.now())
                .modifiedAt(LocalDateTime.now())
                .build();
        store.put(sequence, imageCategory);

        return (S) imageCategory;
    }

    @Override
    public <S extends ImageCategory> List<S> saveAll(Iterable<S> entities) {
        return List.of();
    }

    @Override
    public Optional<ImageCategory> findById(Long aLong) {
        return Optional.empty();
    }

    @Override
    public boolean existsById(Long aLong) {
        List<ImageCategory> findList = store.values()
                .stream()
                .filter(imageCategory -> imageCategory.getId().equals(aLong))
                .toList();

        return !findList.isEmpty();
    }

    @Override
    public List<ImageCategory> findAll() {
        return List.of();
    }

    @Override
    public List<ImageCategory> findAllById(Iterable<Long> longs) {
        return List.of();
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void deleteById(Long aLong) {

    }

    @Override
    public void delete(ImageCategory entity) {

    }

    @Override
    public void deleteAllById(Iterable<? extends Long> longs) {

    }

    @Override
    public void deleteAll(Iterable<? extends ImageCategory> entities) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public List<ImageCategory> findAll(Sort sort) {
        return List.of();
    }

    @Override
    public Page<ImageCategory> findAll(Pageable pageable) {
        return null;
    }
}
