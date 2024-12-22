package com.krince.memegle.domain.image.repository.fake;

import com.krince.memegle.domain.image.entity.Bookmark;
import com.krince.memegle.domain.image.repository.BookmarkRepository;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public class FakeBookmarkRepository implements BookmarkRepository {
    @Override
    public Optional<Bookmark> findByImageIdAndUserId(Long imageId, Long userId) {
        return Optional.empty();
    }

    @Override
    public void flush() {

    }

    @Override
    public <S extends Bookmark> S saveAndFlush(S entity) {
        return null;
    }

    @Override
    public <S extends Bookmark> List<S> saveAllAndFlush(Iterable<S> entities) {
        return List.of();
    }

    @Override
    public void deleteAllInBatch(Iterable<Bookmark> entities) {

    }

    @Override
    public void deleteAllByIdInBatch(Iterable<Long> longs) {

    }

    @Override
    public void deleteAllInBatch() {

    }

    @Override
    public Bookmark getOne(Long aLong) {
        return null;
    }

    @Override
    public Bookmark getById(Long aLong) {
        return null;
    }

    @Override
    public Bookmark getReferenceById(Long aLong) {
        return null;
    }

    @Override
    public <S extends Bookmark> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends Bookmark> List<S> findAll(Example<S> example) {
        return List.of();
    }

    @Override
    public <S extends Bookmark> List<S> findAll(Example<S> example, Sort sort) {
        return List.of();
    }

    @Override
    public <S extends Bookmark> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends Bookmark> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends Bookmark> boolean exists(Example<S> example) {
        return false;
    }

    @Override
    public <S extends Bookmark, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return null;
    }

    @Override
    public <S extends Bookmark> S save(S entity) {
        return null;
    }

    @Override
    public <S extends Bookmark> List<S> saveAll(Iterable<S> entities) {
        return List.of();
    }

    @Override
    public Optional<Bookmark> findById(Long aLong) {
        return Optional.empty();
    }

    @Override
    public boolean existsById(Long aLong) {
        return false;
    }

    @Override
    public List<Bookmark> findAll() {
        return List.of();
    }

    @Override
    public List<Bookmark> findAllById(Iterable<Long> longs) {
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
    public void delete(Bookmark entity) {

    }

    @Override
    public void deleteAllById(Iterable<? extends Long> longs) {

    }

    @Override
    public void deleteAll(Iterable<? extends Bookmark> entities) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public List<Bookmark> findAll(Sort sort) {
        return List.of();
    }

    @Override
    public Page<Bookmark> findAll(Pageable pageable) {
        return null;
    }
}
