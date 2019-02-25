package com.controllers;

import com.database.PostTagEntity;
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
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.Collection;

@RestController
public class ControllerPost {

    private final PostService postService;
    private final GalleryService galleryService;
    private final CustomersService customersService;
    public final TagService tagService;

    @Autowired
    public ControllerPost(PostService postService, GalleryService galleryService, CustomersService customersService, TagService tagService) {
        this.postService = postService;
        this.galleryService = galleryService;
        this.customersService = customersService;
        this.tagService = tagService;
    }

    @GetMapping("/getPosts")
    String getPosts() {
        String answer = postService.getAllPosts();
        if (answer != null)
            return answer;
        else return "Empty request";
    }

    @GetMapping("/getAllPhotosOfTheFilm")
    String getAllPhotosOfTheFilm() {
        String answer = galleryService.getAllFilmGallery();
        if (answer != null)
            return answer;
        else return "Empty request";
    }

    @GetMapping("/getAllPhotosOfTheActor")
    String getAllPhotosOfTheActor() {
        String answer = galleryService.getAllActorGallery();
        if (answer != null)
            return answer;
        else return "Empty request";
    }

    @GetMapping("/getAllPhotosOfTheStudio")
    String getAllPhotosOfTheStudio() {
        String answer = galleryService.getAllStudioGallery();
        if (answer != null)
            return answer;
        else return "Empty request";
    }

    //Todo вот новый пост

    @RequestMapping(value = "/newPost", method = RequestMethod.POST)
    public ResponseEntity newPost(@RequestParam("username") String username,
                                  @RequestParam("body") String body,
                                  @RequestParam("img") String img) {
        java.util.Date d = new java.util.Date();
        Date date = new Date(d.getYear(), d.getMonth(), d.getDay());
        Collection<PostTagEntity> postTags = null;
        postService.addNewPost(img, date, body, customersService.findByLog(username).getId(), null);
        return ResponseEntity.ok(true);
    }


    //Todo добавить теги
    @RequestMapping(value = "/setTags", method = RequestMethod.POST)
    public ResponseEntity addTag(@RequestParam("id") int id,
                                 @RequestParam("bodyTags") String body,
                                 @RequestParam("img") String img) {

        JSONArray jsonArray = new JSONArray(body);

        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                JSONObject json = (JSONObject) (jsonArray.get(i));
                String someString = json.getString("tag");
                postService.addTagForPost(2, tagService.findTag(someString).getId());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return ResponseEntity.ok(true);
    }

}