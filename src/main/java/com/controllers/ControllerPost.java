package com.controllers;

import com.database.CustomersEntity;
import com.database.PostTagEntity;
import com.database.TagEntity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.service.CustomersService;
import com.service.GalleryService;
import com.service.PostService;
import com.service.TagService;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.Collection;
import java.util.List;

@RestController
public class ControllerPost {

    private final PostService postService;
    private final GalleryService galleryService;
    private final CustomersService customersService;
    private final TagService tagService;

    @Autowired
    public ControllerPost(PostService postService, GalleryService galleryService, CustomersService customersService, TagService tagService) {
        this.postService = postService;
        this.galleryService = galleryService;
        this.customersService = customersService;
        this.tagService = tagService;
    }

    @Secured({"CUSTOMER", "ACTOR", "ANALYST", "STUDIO"})
    @GetMapping("/getPosts")
    ResponseEntity getPosts() {
        String answer = postService.getAllPosts();
        if (answer != null)
            return ResponseEntity.ok(answer);
        else return (ResponseEntity) ResponseEntity.notFound();
    }

    @Secured({"CUSTOMER", "ACTOR", "ANALYST", "STUDIO"})
    @GetMapping("/getAllPhotosOfTheFilm")
    ResponseEntity getAllPhotosOfTheFilm() {
        String answer = galleryService.getAllFilmGallery();
        if (answer == null) return (ResponseEntity) ResponseEntity.notFound();
        return ResponseEntity.ok(answer);
    }

    @Secured({"CUSTOMER", "ACTOR", "ANALYST", "STUDIO"})
    @RequestMapping("/getAllPhotosOfTheActor")
    ResponseEntity getAllPhotosOfTheActor() {
        String answer = galleryService.getAllActorGallery();
        if (answer == null) return (ResponseEntity) ResponseEntity.notFound();
        return ResponseEntity.ok(answer);
    }

    @Secured({"CUSTOMER", "ACTOR", "ANALYST", "STUDIO"})
    @GetMapping("/getAllPhotosOfTheStudio")
    ResponseEntity getAllPhotosOfTheStudio() {
        String answer = galleryService.getAllStudioGallery();
        if (answer != null)
            return ResponseEntity.ok(answer);
        else return (ResponseEntity) ResponseEntity.notFound();
    }


    @Secured({"ACTOR", "STUDIO"})
    @RequestMapping(value = "/newPost", method = RequestMethod.POST)
    public ResponseEntity newPost(@RequestParam("body") String body,
                                  @RequestParam("img") String img) {
        CustomersEntity customer = customersService.findByLog(
                SecurityContextHolder.getContext().getAuthentication().getName());

        if (customer != null) {
            java.util.Date d = new java.util.Date();
            Date date = new Date(d.getYear(), d.getMonth(), d.getDay());
            postService.addNewPost(img, date, body, customer.getId(), null);
            return ResponseEntity.ok(true);
        }
        return new ResponseEntity(HttpStatus.UNAUTHORIZED);
    }


    @Secured({"CUSTOMER", "ACTOR", "ANALYST", "STUDIO"})
    @RequestMapping(value = "/setTags", method = RequestMethod.POST)
    public ResponseEntity addTag(@RequestParam("id_tag") int id_tag,
                                 @RequestParam("bodyTags") String body) {
        JSONArray jsonArray = new JSONArray(body);
        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                JSONObject json = (JSONObject) (jsonArray.get(i));
                String someString = json.getString("tag");
                postService.addTagForPost(id_tag, tagService.findTag(someString).getId());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return ResponseEntity.ok(true);
    }

    @Secured({"CUSTOMER", "ACTOR", "ANALYST", "STUDIO"})
    @RequestMapping(value = "/setTags", method = RequestMethod.GET)
    public ResponseEntity allTag() {
        List<TagEntity> tags;
        tags = tagService.allTags();
        String json = new Gson().toJson(tags);
        return ResponseEntity.ok(json);
    }

    @Secured({"ACTOR", "STUDIO"})
    @RequestMapping(value = "/newTag", method = RequestMethod.POST)
    public ResponseEntity newTag(@RequestParam("name") String name) {
        try {
            tagService.newTag(name);
            return ResponseEntity.ok(true);
        } catch (Exception e) {
            return (ResponseEntity) ResponseEntity.badRequest();
        }
    }


}