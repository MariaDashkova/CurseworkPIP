package com.service;

import com.database.*;
import com.repository.ActorRepository;
import com.repository.CustomersRepository;
import com.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
public class ActorService {
    private final ActorRepository actorRepository;
    private final CustomersRepository customersRepository;
    private PostRepository postRepository;

    @Autowired
    public ActorService(ActorRepository actorRepository, CustomersRepository customersRepository) {
        this.actorRepository = actorRepository;
        this.customersRepository = customersRepository;
    }

    public String[] studioInfo(int id) {

        String[] info = new String[3];

        info[0] = (customersRepository.findById(id).getName());
        info[1] = (actorRepository.findByIdActor(id).getAboutMe());
        info[2] = String.valueOf((customersRepository.findById(id).getImg()));

        return info;
    }

    public double actorScore(int id) {
        Collection<ScoreActorEntity> actScore = actorRepository.findByIdActor(1).getScoreActors();
        Float sum = 1f;

        for (ScoreActorEntity e : actScore) sum += e.getScore();

        return Math.round((sum / actScore.size()) * 100.0) / 100.0;
    }

    public Collection<GalleryActorEntity> getActorGallery(int id) {
        //получаем все фото от студии по ее id в коллекцию
        Collection<GalleryActorEntity> stGallery = actorRepository.findByIdActor(id).getGalleryActors();
        return stGallery;
    }

    public int getActorPosts(int id) {
        List<PostEntity> studioPosts = postRepository.findAllUsers(id);
        //получилимножество постов, где каждый пост целиком
        return studioPosts.size();

    }//Todo add
    public Collection<PostEntity> allPosts(int id) {
        try {
            return postRepository.findByIdUsers(id);
        } catch (NullPointerException e) {
            return null;
        }
    }
    //ToDO add
    public Collection<FollowerActorEntity> getFollowers(int id) {
        return actorRepository.findByIdActor(id).getFollowerActors();
    }

}
