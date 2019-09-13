package com.controllers;

import com.database.CustomersEntity;
import com.database.FollowerActorEntity;
import com.database.GalleryActorEntity;
import com.database.PostEntity;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.service.ActorService;
import com.service.CustomersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collection;

@Controller
public class ControllerActor {
    private final ActorService actorService;
    private final CustomersService customersService;
    final char dm = (char) 34;
    private CustomersEntity actor = null;

    @Autowired
    public ControllerActor(ActorService actorService, CustomersService customersService) {
        this.actorService = actorService;
        this.customersService = customersService;
    }

    @Secured({"CUSTOMER", "ACTOR", "ANALYST", "STUDIO"})
    @RequestMapping(value = "/getActorInfo", method = RequestMethod.GET)
    public ResponseEntity getInfo(@RequestParam("id_actor") int id_actor) {
        return ResponseEntity.ok(actorService.studioInfo(id_actor));
    }

    @Secured({"CUSTOMER", "ACTOR", "ANALYST", "STUDIO"})
    @RequestMapping(value = "/getActorPhotos", method = RequestMethod.GET)
    public ResponseEntity getSub(@RequestParam("id") int id) {
        actor = customersService.findById(id);
        StringBuilder builder = new StringBuilder();

        Collection<GalleryActorEntity> Gallery = actorService.getActorGallery(actor.getId());
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().setPrettyPrinting().create();
        builder.append("[");

        for (GalleryActorEntity g : Gallery
        ) {
            String s = gson.toJson(g);
            builder.append(s);
            builder.append(",");
            g = null;
        }

        builder.setLength(builder.length() - 1);
        builder.append("]");
        return ResponseEntity.ok(builder.toString());
    }

    @Secured({"CUSTOMER", "ACTOR", "ANALYST", "STUDIO"})
    @RequestMapping(value = "/actorFollowers", method = RequestMethod.GET)
    public ResponseEntity getActorFollowers() {
        StringBuilder builder = new StringBuilder();

        Collection<FollowerActorEntity> followers = actorService.getFollowers(actor.getId());
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().setPrettyPrinting().create();
        builder.append("[");

        for (FollowerActorEntity f : followers
        ) {
            String s = gson.toJson(customersService.findById(f.getIdCustomers()));
            builder.append(s);
            builder.append(",");
            s = null;
        }

        builder.setLength(builder.length() - 1);
        builder.append("]");
        return ResponseEntity.ok(builder.toString());
    }

    @Secured({"CUSTOMER", "ACTOR", "ANALYST", "STUDIO"})
    @RequestMapping(value = "/actorScore", method = RequestMethod.GET)
    public ResponseEntity getActorScore(@RequestParam("id") int id) {
        actor = customersService.findById(id);
        return ResponseEntity.ok(actorService.actorScore(actor.getId()));
    }

    @Secured({"CUSTOMER", "ACTOR", "ANALYST", "STUDIO"})
    @RequestMapping(value = "/actorPosts", method = RequestMethod.GET)
    public ResponseEntity getActorPosts(@RequestParam("id") int id) {
        actor = customersService.findById(id);
        Collection<PostEntity> posts = actorService.allPosts(actor.getId());
        StringBuilder builder = new StringBuilder();
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().setPrettyPrinting().create();
        builder.append("[");

        for (PostEntity p : posts
        ) {
            String s = gson.toJson(p);
            builder.append(s);
            builder.append(",");
            s = null;
        }
        builder.setLength(builder.length() - 1);
        builder.append("]");
        return ResponseEntity.ok(builder.toString());
    }
}
