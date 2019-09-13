package com.controllers;

import com.database.CustomersEntity;
import com.service.CustomersService;
import com.service.VotesService;
import com.service.VotesUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
public class ControllerVotes {

    private final VotesService votesService;
    private final VotesUserService votesUserService;
    private final CustomersService customersService;

    @Autowired
    public ControllerVotes(VotesService votesService, VotesUserService votesUserService, CustomersService customersService) {
        this.votesService = votesService;
        this.votesUserService = votesUserService;
        this.customersService = customersService;
    }

    @Secured({"CUSTOMER"})
    @RequestMapping(value = "/createNewVote", method = RequestMethod.POST)
    ResponseEntity createNewVote(@RequestParam("id_film") int id_film,
                                 @RequestParam("name") String name) {
        String newVote = votesService.addNewVote(id_film, name);
        if (newVote != null)
            return ResponseEntity.ok(newVote);
        else return (ResponseEntity) ResponseEntity.status(HttpStatus.BAD_GATEWAY);
    }

    @Secured({"TECHNICAL_SUPPORT"})
    @GetMapping("/accessVote")
    ResponseEntity accessVote() {
        String vote = votesService.findAllAccessVote();
        if (vote != null)
            return ResponseEntity.ok(vote);
        else return (ResponseEntity) ResponseEntity.notFound();
    }

    @Secured({"CUSTOMER"})
    @GetMapping("/checkUserVote")
    ResponseEntity checkUserVote(@RequestParam("id_vote") int id_vote,
                                 @RequestParam("choice") boolean choice) {
        CustomersEntity customer = customersService.findByPas(
                SecurityContextHolder.getContext().getAuthentication().getCredentials().toString());
        if (customer == null) return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        if (votesUserService.checkUserVote(customer.getId(), id_vote))
            return (ResponseEntity) ResponseEntity.status(HttpStatus.LOCKED);
        if (votesService.recalculation(id_vote, choice) == null) return (ResponseEntity) ResponseEntity.notFound();
        return ResponseEntity.ok(true);
    }

    @Secured({"CUSTOMER", "ACTOR", "ANALYST", "STUDIO"})
    @GetMapping("/findAllVotesForFilm")
    ResponseEntity findAllVotesForFilm(@RequestParam("id_film") int id_film) {
        String answer = votesService.findAllVotesForFilm(id_film);
        if (answer == null) return (ResponseEntity) ResponseEntity.notFound();
        return ResponseEntity.ok(answer);
    }

    @Secured({"CUSTOMER", "ACTOR", "ANALYST", "STUDIO"})
    @GetMapping("/getInfoForVote")
    ResponseEntity getInfoForVote(@RequestParam("id_vote") int id_vote) {
        String answer = votesService.findInfoForVote(id_vote);
        if (answer == null) return (ResponseEntity) ResponseEntity.notFound();
        return ResponseEntity.ok(answer);
    }

    @Secured({"CUSTOMER", "ACTOR", "ANALYST", "STUDIO"})
    @GetMapping("/setChoice")
    ResponseEntity setPositive(@RequestParam("id_vote") int id_vote,
                               @RequestParam("choice") boolean choice) {
        votesService.recalculation(id_vote, choice);
        return (ResponseEntity) ResponseEntity.ok();
    }


}
