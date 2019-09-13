package com.service;

import com.database.*;
import com.repository.CustomersRepository;
import com.repository.FollowerActorRepository;
import com.repository.FollowerAnalystRepository;
import com.repository.FollowerStudioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

@Service
public class FollowerService {

    private final FollowerActorRepository followerActorRepository;
    private final FollowerAnalystRepository followerAnalystRepository;
    private final FollowerStudioRepository followerStudioRepository;

    @Autowired
    public FollowerService(FollowerStudioRepository followerStudioRepository,
                           FollowerAnalystRepository followerAnalystRepository,
                           FollowerActorRepository followerActorRepository) {
        this.followerActorRepository = followerActorRepository;
        this.followerAnalystRepository = followerAnalystRepository;
        this.followerStudioRepository = followerStudioRepository;
    }

    public String insertIntoFollowerStudio(int id_studio, int id_customer) {
        try {
            FollowerStudioEntity followerStudioEntity = new FollowerStudioEntity();
            followerStudioEntity.setIdStudio(id_studio);
            followerStudioEntity.setIdCustomers(id_customer);
            followerStudioRepository.save(followerStudioEntity);
            return "You signed up for the Studio";
        } catch (NullPointerException ex) {
            return null;
        }
    }

    public String insertIntoFollowerActor(int id_actor, int id_customer) {
        try {
            FollowerActorEntity followerEntity = new FollowerActorEntity();
            followerEntity.setIdCustomers(id_customer);
            followerEntity.setIdActor(id_actor);
            followerActorRepository.save(followerEntity);
            return "You signed up for the Actor";
        } catch (NullPointerException ex) {
            return null;
        }
    }

    public String insertIntoFollowerAnalyst(int id_analyst, int id_customer) {
        try {
            FollowerAnalystEntity analystFollowerEntity = new FollowerAnalystEntity();
            analystFollowerEntity.setIdAnalyst(id_analyst);
            analystFollowerEntity.setIdCustomers(id_customer);
            followerAnalystRepository.save(analystFollowerEntity);
            return "You signed up for the Analyst";
        } catch (NullPointerException ex) {
            return null;
        }
    }

    public void deleteFollowersStudio(int id_studio, int id_customer) {
        try {
            followerStudioRepository.deleteAllByIdCustomersLikeAndIdStudioLike(id_customer, id_studio);
        } catch (NullPointerException ignored) {
        }
    }

    public void deleteFollowersActor(int id_actor, int id_customer) {
        try {
            followerActorRepository.deleteAllByIdCustomersLikeAndIdActorLike(id_customer, id_actor);
        } catch (NullPointerException ignored) {
        }
    }

    public void deleteFollowersAnalyst(int id_analyst, int id_customer) {
        try {
            followerAnalystRepository.deleteAllByIdCustomersLikeAndIdAnalystLike(id_customer, id_analyst);
        } catch (NullPointerException ignored) {
        }
    }

    public String getForFollowerIMGOfStudio(int id_customer) {
        String imgAndName = "";
        try {
            List<CustomersEntity> subscribeToTheStudios2 = new LinkedList<>();
            List<FollowerStudioEntity> subscribeToTheStudios = followerStudioRepository.findAll();
            for (FollowerStudioEntity follower : subscribeToTheStudios) {
                if (follower.getIdCustomers() == id_customer)
                    subscribeToTheStudios2.add(follower.getStudios().getCustomers());
            }
            for (CustomersEntity studioEntityForFollowers : subscribeToTheStudios2) {
                imgAndName = "Name: " + studioEntityForFollowers.getName()
                        + "Photo: " + studioEntityForFollowers.getImg() + "   ";
            }
            return imgAndName;
        } catch (NullPointerException ex) {
            return null;
        }
    }

    public String getForFollowerIMGOfActor(int id_customer) {
        String imgAndName = "";
        try {
            List<CustomersEntity> subscribeToTheActors2 = new LinkedList<>();
            List<FollowerActorEntity> subscribeToTheActors = followerActorRepository.findAll();
            for (FollowerActorEntity follower : subscribeToTheActors) {
                if (follower.getIdCustomers() == id_customer)
                    subscribeToTheActors2.add(follower.getActors().getCustomerActor());
            }
            for (CustomersEntity actorEntityForFollowers : subscribeToTheActors2) {
                imgAndName = "Name: " + actorEntityForFollowers.getName()
                        + "Photo: " + actorEntityForFollowers.getImg() + "   ";
            }
            return imgAndName;
        } catch (NullPointerException ex) {
            return null;
        }
    }

    public String getForFollowerIMGOfAnalyst(int id_customer) {
        String imgAndName = "";
        try {
            List<CustomersEntity> subscribeToTheAnalyst2 = new LinkedList<>();
            List<FollowerAnalystEntity> subscribeToTheAnalysts = followerAnalystRepository.findAll();
            for (FollowerAnalystEntity follower : subscribeToTheAnalysts) {
                if (follower.getIdCustomers() == id_customer)
                    subscribeToTheAnalyst2.add(follower.getAnalysts().getCustomersAnalyst());
            }
            for (CustomersEntity actorEntityForFollowers : subscribeToTheAnalyst2) {
                imgAndName = "Name: " + actorEntityForFollowers.getName()
                        + "Photo: " + actorEntityForFollowers.getImg() + "   ";
            }
            return imgAndName;
        } catch (NullPointerException ex) {
            return null;
        }
    }

}
