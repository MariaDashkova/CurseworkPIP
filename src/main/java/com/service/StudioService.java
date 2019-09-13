package com.service;

import com.database.GalleryStudioEntity;
import com.database.PostEntity;
import com.database.ScoreStudioEntity;
import com.database.VerificationEntity;
import com.repository.CustomersRepository;
import com.repository.PostRepository;
import com.repository.StudioRpository;
import com.repository.VereficationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

@Service
public class StudioService {
    private final StudioRpository studioRpository;
    private final CustomersRepository customersRepository;
    private final PostRepository postRepository;
    private final VereficationRepository vereficationRepository;

    @Autowired
    public StudioService(StudioRpository studioRpository, CustomersRepository customersRepository, PostRepository postRepository, VereficationRepository vereficationRepository) {
        this.studioRpository = studioRpository;
        this.customersRepository = customersRepository;
        this.postRepository = postRepository;
        this.vereficationRepository = vereficationRepository;
    }

    public String[] studioInfo(int id) {

        String[] info = new String[3];

        info[0] = (customersRepository.findById(id).getName());
        info[1] = (studioRpository.findByIdStudio(id).getAboutMe());
        info[2] = String.valueOf((customersRepository.findById(id).getImg()));

        return info;
    }

    public void setInfo(int id_studio, String info){
        studioRpository.updateInfo(info, id_studio);
    }


    public Collection<GalleryStudioEntity> getStudioGallery(int id) {
        //получаем все фото от студии по ее id в коллекцию
        Collection<GalleryStudioEntity> stGallery = studioRpository.findByIdStudio(id).getGalleryStudios();
        return stGallery;
    }

    public int getStudioPosts(int id) {
        List<PostEntity> studioPosts = postRepository.findAllUsers(id);
        //получилимножество постов, где каждый пост целиком
        return studioPosts.size();
    }

    public Long getStudioPurse(int id){
        return studioRpository.findByIdStudio(id).getPurse();
    }

    //верефикация актеров для конкретной студии
    public void actorsVerif(int id, int access){
        LinkedList<VerificationEntity> actors = vereficationRepository.selectActorsToVer(id);

        for(VerificationEntity v : actors ){
            customersRepository.updateUserSetLevelAccessForId(access,v.getIdCustomers());
        }
    }

    public LinkedList<VerificationEntity> actors(int id, int id_actor, int access) {
        LinkedList<VerificationEntity> actors = vereficationRepository.selectActorsToVer(id);
        return actors;

    }

    public void setPurse(int id_studio, long purse){
        studioRpository.updatePurse(purse, id_studio);
    }

    public String getInfo(int id_studio){
        return studioRpository.findByIdStudio(id_studio).getAboutMe();
    }

    }
