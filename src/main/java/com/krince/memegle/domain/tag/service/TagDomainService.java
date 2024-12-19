package com.krince.memegle.domain.tag.service;

import com.krince.memegle.domain.tag.entity.Tag;
import com.krince.memegle.domain.tag.entity.TagMap;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TagDomainService {

    List<Tag> getTagListFromTagNameList(String[] tagNameList);

    List<TagMap> registTagMapList(List<TagMap> tagMapList);

    List<Tag> getRegistedTagList(String[] tagNameList);

    List<Tag> getNotRegistedTagList(String[] tagNameList);

    List<Tag> registTagList(List<Tag> tagList);
}
