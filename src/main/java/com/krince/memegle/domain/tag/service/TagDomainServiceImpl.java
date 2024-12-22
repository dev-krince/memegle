package com.krince.memegle.domain.tag.service;

import com.krince.memegle.domain.tag.entity.Tag;
import com.krince.memegle.domain.tag.entity.TagMap;
import com.krince.memegle.domain.tag.repository.TagMapRepository;
import com.krince.memegle.domain.tag.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TagDomainServiceImpl implements TagDomainService {

    private final TagRepository tagRepository;
    private final TagMapRepository tagMapRepository;

    @Override
    public List<Tag> getTagListFromTagNameList(String[] tagNameList) {
        return Arrays.stream(tagNameList)
                .map(tag -> tagRepository.findByTagName(tag)
                        .orElseThrow(NoSuchElementException::new))
                .toList();
    }

    @Override
    public List<TagMap> registTagMapList(List<TagMap> tagMapList) {
        return tagMapRepository.saveAll(tagMapList);
    }

    @Override
    public List<Tag> getRegistedTagList(String[] tagNameList) {
        return Arrays.stream(tagNameList)
                .map(tagRepository::findByTagName)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
    }

    @Override
    public List<Tag> getNotRegistedTagList(String[] tagNameList) {
        List<Tag> notRegistedTagList = new ArrayList<>();

        for (String tagName : tagNameList) {
            boolean isNotRegistTagName = tagRepository.findByTagName(tagName).isEmpty();

            if (isNotRegistTagName) {
                Tag notRegistTag = Tag.of(tagName);
                notRegistedTagList.add(notRegistTag);
            }
        }

        return notRegistedTagList;
    }

    @Override
    public List<Tag> registTagList(List<Tag> notRegistedTagList) {
        return tagRepository.saveAll(notRegistedTagList);
    }
}
