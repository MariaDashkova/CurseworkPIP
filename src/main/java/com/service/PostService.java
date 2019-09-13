package com.service;

import com.database.PostEntity;
import com.database.PostTagEntity;
import com.database.TagEntity;
import com.repository.PostRepository;
import com.repository.PostTagRepository;
import com.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.Collection;
import java.util.List;

@Service
public class PostService {
    private final PostRepository postRepository;
    private final PostTagRepository postTagRepository;
    private final TagRepository tagRepository;

    @Autowired
    public PostService(PostRepository postRepository, PostTagRepository postTagRepository, TagRepository tagRepository) {
        this.postRepository = postRepository;

        this.postTagRepository = postTagRepository;
        this.tagRepository = tagRepository;
    }

    public String getAllPosts() {
        try {
            List<PostEntity> postEntities = postRepository.findAll();
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("[");
            for (PostEntity post : postEntities) {
                stringBuilder.append("{\"name\":").append(post.getCustomers().getName())
                        .append(",\"tag\"").append(post.getPostTags())
                        .append(",\"body\"").append(post.getBody()).append("},");
            }
            stringBuilder.setLength(stringBuilder.length() - 1);
            stringBuilder.append("]");
            return String.valueOf(stringBuilder);
        } catch (NullPointerException ex) {
            return null;
        }
    }

    public void addNewPost(String img, Date date, String body, Integer userUsOwnerId, Collection<PostTagEntity> postTags) {
        PostEntity pe = new PostEntity();
        pe.setImg(img);
        pe.setCreateDate(date);
        pe.setBody(body);
        pe.setUserUsOwnerId(userUsOwnerId);
        pe.setPostTags(postTags);
        postRepository.save(pe);
    }


    public void addTagForPost(int idPost, int idTag) {
        postTagRepository.insert(idPost, idTag);
        TagEntity tagEntity = tagRepository.findById(idTag);
        tagEntity.setCount(tagEntity.getCount() + 1);
    }


}
