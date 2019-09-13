package com.service;

import com.database.TagEntity;
import com.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

public class TagService {
    private final TagRepository tagRepository;

    @Autowired
    public TagService(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    public TagEntity findTag(String tag) {
        return tagRepository.findByTagName(tag);
    }

    public List<TagEntity> allTags(){return tagRepository.findAll();}


    public void newTag(String name){
        TagEntity tag = new TagEntity();
        tag.setTagName(name);
        tag.setCount(0);
        tagRepository.save(tag);
    }
}
