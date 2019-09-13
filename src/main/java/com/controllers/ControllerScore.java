package com.controllers;

import com.database.CustomersEntity;
import com.service.CustomersService;
import com.service.ScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
public class ControllerScore {

    private final ScoreService scoreService;
    private final CustomersService customersService;

    @Autowired
    public ControllerScore(ScoreService scoreService, CustomersService customersService) {
        this.scoreService = scoreService;
        this.customersService = customersService;
    }

    @Secured({"CUSTOMER", "ACTOR", "ANALYST", "STUDIO"})
    @GetMapping("/getScoreAnalyst")
    ResponseEntity getScoreAnalyst(@RequestParam("id_analyst") int id_analyst) {
        String answer = scoreService.findAllScoreForAnalyst(id_analyst);
        if (answer != null)
            return ResponseEntity.ok(answer);
        else return (ResponseEntity) ResponseEntity.notFound();
    }

    @Secured({"CUSTOMER", "ACTOR", "ANALYST", "STUDIO"})
    @GetMapping("/getScoreActor")
    ResponseEntity getScoreActor(@RequestParam("id_actor") int id_actor) {
        String answer = scoreService.findAllScoreForActor(id_actor);
        if (answer != null)
            return ResponseEntity.ok(answer);
        else return (ResponseEntity) ResponseEntity.notFound();
    }

    @Secured({"CUSTOMER", "ACTOR", "ANALYST", "STUDIO"})
    @GetMapping("/getScoreStudio")
    ResponseEntity getScoreStudio(@RequestParam("id_studio") int id_studio) {
        String answer = scoreService.findAllScoreForStudio(id_studio);
        if (answer != null)
            return ResponseEntity.ok(answer);
        else return (ResponseEntity) ResponseEntity.notFound();
    }

    @Secured({"CUSTOMER", "ACTOR", "ANALYST", "STUDIO"})
    @GetMapping("/getScoreFilm")
    ResponseEntity getScoreFilm(@RequestParam("id_film") int id_film) {
        String answer = scoreService.findAllScoreForFilm(id_film);
        if (answer != null)
            return ResponseEntity.ok(answer);
        else return (ResponseEntity) ResponseEntity.notFound();
    }

    @Secured({"CUSTOMER", "ACTOR", "STUDIO"})
    @RequestMapping(value = "/setScoreAnalyst", method = RequestMethod.POST)
    ResponseEntity setScoreAnalyst(@RequestParam("id_analyst") int id_analyst,
                                   @RequestParam("score") int score) {
        CustomersEntity customer = customersService.findByLog(
                SecurityContextHolder.getContext().getAuthentication().getName());
        if (customer == null) return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        scoreService.insertNewScoreForAnalyst(id_analyst, customer.getId(), score);
        return (ResponseEntity) ResponseEntity.ok();
    }

    @Secured({"CUSTOMER", "ANALYST", "STUDIO"})
    @RequestMapping(value = "/setScoreActor", method = RequestMethod.POST)
    ResponseEntity setScoreActor(@RequestParam("id_actor") int id_actor,
                                 @RequestParam("score") int score) {
        CustomersEntity customer = customersService.findByLog(
                SecurityContextHolder.getContext().getAuthentication().getName());
        if (customer == null) return new ResponseEntity(HttpStatus.UNAUTHORIZED);

        scoreService.insertNewScoreForActor(id_actor, customer.getId(), score);
        return (ResponseEntity) ResponseEntity.ok();
    }

    @Secured({"CUSTOMER", "ANALYST", "ACTOR"})
    @RequestMapping(value = "/setScoreStudio", method = RequestMethod.POST)
    ResponseEntity setScoreStudio(@RequestParam("id_studio") int id_studio,
                                  @RequestParam("score") int score) {
        CustomersEntity customer = customersService.findByLog(
                SecurityContextHolder.getContext().getAuthentication().getName());
        if (customer == null) return new ResponseEntity(HttpStatus.UNAUTHORIZED);

        scoreService.insertNewScoreForStudio(id_studio, customer.getId(), score);
        return (ResponseEntity) ResponseEntity.ok();
    }

    @Secured({"CUSTOMER", "ACTOR", "STUDIO", "ANALYST"})
    @RequestMapping(value = "/setScoreFilm", method = RequestMethod.POST)
    ResponseEntity setScoreFilm(@RequestParam("id_film") int id_film,
                                @RequestParam("score") int score) {
        CustomersEntity customer = customersService.findByLog(
                SecurityContextHolder.getContext().getAuthentication().getName());
        if (customer == null) return new ResponseEntity(HttpStatus.UNAUTHORIZED);

        scoreService.insertNewScoreForFilm(id_film, customer.getId(), score);
        return (ResponseEntity) ResponseEntity.ok();

    }

}
