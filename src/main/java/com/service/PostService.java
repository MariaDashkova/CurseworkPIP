package com.service;

import com.database.PostEntity;
import com.database.PostTagEntity;
import com.database.PostTagEntityPK;
import com.repository.PostRepository;
import com.repository.PostTagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.Collection;
import java.util.List;

@Service
public class PostService {
    private final PostRepository postRepository;
private final PostTagRepository postTagRepository;
    @Autowired
    public PostService(PostRepository postRepository, PostTagRepository postTagRepository) {
        this.postRepository = postRepository;

        this.postTagRepository = postTagRepository;
    }

    public String getAllPosts() {
        try {
            List<PostEntity> postEntities = postRepository.findAll();
            return "All was saved in list.";
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


    public void addTagForPost(int idPost, int idTag){
//        PostTagEntity pt = new PostTagEntity();
//        pt.setIdPost(idPost);
//        pt.setIdTag(idTag);
//
//        postTagRepository.save(pt);
        postTagRepository.insert(idPost, idTag);
    }


}
