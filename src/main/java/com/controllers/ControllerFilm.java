package com.controllers;

import com.database.CustomersEntity;
import com.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Date;

@RestController
public class ControllerFilm {

    private final FilmService filmService;
    private final ChatService chatService;
    private final ScoreService scoreService;
    private final GalleryService galleryService;
    private final CustomersService customersService;

    @Autowired
    public ControllerFilm(FilmService filmService, ChatService chatService, ScoreService scoreService, GalleryService galleryService, CustomersService customersService) {
        this.filmService = filmService;
        this.chatService = chatService;
        this.scoreService = scoreService;
        this.galleryService = galleryService;
        this.customersService = customersService;
    }
    @Secured({"CUSTOMER", "ACTOR", "ANALYST", "STUDIO"})
    @GetMapping("/getFeedRecommendationForUser")
    ResponseEntity getFeedRecommendationForUser() {

        CustomersEntity customer = customersService.findByLog(
                SecurityContextHolder.getContext().getAuthentication().getName());
        if (customer == null) return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        String info = filmService.getInfoForUserNewsFeed(customer.getId());
        if (info == null)
            return (ResponseEntity) ResponseEntity.notFound();
        else return ResponseEntity.ok(info);
    }

    @Secured({"CUSTOMER", "ACTOR", "ANALYST", "STUDIO"})
    @GetMapping(value = "/findTheMostInterestingAnnotation", produces = "application/json;charset=UTF-8")
    ResponseEntity findTheMostInterestingAnnotation(@RequestParam("film_id") int film_id) {
        String info = filmService.findTheMostInterestingAnnotation(film_id);
        if (info == null)
            return (ResponseEntity) ResponseEntity.notFound();
        else return ResponseEntity.ok(info);
    }

    @Secured({"CUSTOMER", "ACTOR", "ANALYST", "STUDIO"})
    @GetMapping("/getPopularFilms")
    ResponseEntity getFilms() {
        String info = filmService.getInfoForPopularFilms();
        if (info == null)
            return (ResponseEntity) ResponseEntity.notFound();
        else return ResponseEntity.ok(info);
    }

    @Secured({"CUSTOMER"})
    @GetMapping("/getChatForFilm")
    ResponseEntity getChatForFilm(@RequestParam("id_film") int id_film) {
        String chat = chatService.findChatForFilm(id_film);
        if (chat == null)
            return (ResponseEntity) ResponseEntity.notFound();
        else return ResponseEntity.ok(chat);
    }


    @Secured({"CUSTOMER", "ACTOR", "ANALYST", "STUDIO"})
    @GetMapping(value = "/getMainInfoOfTehFilm", produces = "application/json;charset=UTF-8")
    ResponseEntity getInfoFilm(@RequestParam("film_id") int film_id) throws IOException {
        CustomersEntity customer = customersService.findByLog(
                SecurityContextHolder.getContext().getAuthentication().getName());
        if (customer == null) return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        String info = filmService.findMainInfoForTheFilm(film_id, customer.getId());
        try {
            return ResponseEntity.ok(info);
        } catch (Exception e) {
            e.printStackTrace();
            return (ResponseEntity) ResponseEntity.notFound();
        }
    }

//    @GetMapping(value = "getPictureInBASE64",  produces = "application/json;charset=UTF-8")
//    public String getPhotoAsString() {
//        String str = galleryService.getBytesOfPhotoFilm(1);
//        System.out.println(str);
//        return str;
//    }
//
//    @RequestMapping(value = "getBytes", method = RequestMethod.POST)
//    public byte[] getPictureAsBytes(@RequestParam("picture") String pic,
//                                    @RequestParam("meta") String meta,
//                                    @RequestParam("id") int id_film) throws IOException {
//        byte[] bytes = new sun.misc.BASE64Decoder().decodeBuffer(pic);
//        galleryService.savePhotoFilm(id_film, bytes, meta);
//        System.out.println(Arrays.toString(bytes));
//        return bytes;
//    }

    @Secured({"CUSTOMER", "ACTOR", "ANALYST", "STUDIO"})
    @RequestMapping(value ="getPhotosAsString")
    public ResponseEntity getPhotosAsString(@RequestParam("film_id") int film_id){
        return ResponseEntity.ok(galleryService.getPhotoAsString(film_id));
    }

    @Secured({"STUDIO"})
    @RequestMapping(value = "createNewFilm", method = RequestMethod.POST)
    public ResponseEntity createNewFilm(@RequestParam("name") String name,
                                        @RequestParam("create_date") Date create_date,
                                        @RequestParam("body") String body,
                                        @RequestParam("photo") String photo,
                                        @RequestParam("cashbox") int cashbox){
        CustomersEntity customer = customersService.findByLog(
                SecurityContextHolder.getContext().getAuthentication().getName());
        if (customer == null) {
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }
        filmService.saveNewFilm(name, create_date, body, photo, cashbox, customer.getId());
        return ResponseEntity.ok("ok");
    }

}
