package com.controllers;

import com.database.CustomersEntity;
import com.service.CustomersService;
import com.service.FollowerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ControllerFollowers {

    private final FollowerService followerService;
private final CustomersService customersService;
    @Autowired
    public ControllerFollowers(FollowerService followerService, CustomersService customersService) {
        this.followerService = followerService;
        this.customersService = customersService;
    }

    @Secured({"CUSTOMER"})
    @GetMapping("/subscribeOnActor")
    ResponseEntity subscribeOnActor(@RequestParam("id_actor") int id_actor) {
        CustomersEntity customer = customersService.findByLog(
                SecurityContextHolder.getContext().getAuthentication().getName());
        if (customer == null) return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        String answer = followerService.insertIntoFollowerActor(customer.getId(), id_actor);
        if (answer != null)
            return ResponseEntity.ok(answer);
        else return (ResponseEntity) ResponseEntity.notFound();
    }

    @Secured({"CUSTOMER"})
    @GetMapping("/subscribeOnAnalyst")
    ResponseEntity subscribeOnAnalyst(@RequestParam("id_analyst") int id_analyst) {
        CustomersEntity customer = customersService.findByLog(
                SecurityContextHolder.getContext().getAuthentication().getName());
        if (customer == null) return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        String answer = followerService.insertIntoFollowerAnalyst(id_analyst, customer.getId());
        if (answer == null)
            return (ResponseEntity) ResponseEntity.notFound();
        else return ResponseEntity.ok(answer);
    }

    @Secured({"CUSTOMER"})
    @GetMapping("/subscribeOnStudio")
    ResponseEntity subscribeOnStudio(@RequestParam("id_studio") int id_studio) {
        CustomersEntity customer = customersService.findByLog(
                SecurityContextHolder.getContext().getAuthentication().getName());
        if (customer == null) return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        String answer = followerService.insertIntoFollowerStudio(id_studio, customer.getId());
        if (answer != null)
            return ResponseEntity.ok(answer);
        else return null;
    }

    @Secured({"STUDIO", "CUSTOMER"})
    @GetMapping("/deleteFollowerStudio")
    ResponseEntity deleteFollowerStudio(@RequestParam("id_studio") int id_studio) {
        CustomersEntity customer = customersService.findByLog(
                SecurityContextHolder.getContext().getAuthentication().getName());
        if (customer == null) return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        followerService.deleteFollowersStudio(id_studio, customer.getId());
        return ResponseEntity.ok(true);
    }

    @Secured({"ACTOR", "CUSTOMER"})
    @GetMapping("/deleteFollowerActor")
    ResponseEntity deleteFollowerActor(@RequestParam("id_actor") int id_actor) {
        CustomersEntity customer = customersService.findByLog(
                SecurityContextHolder.getContext().getAuthentication().getName());
        if (customer == null) return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        followerService.deleteFollowersActor(id_actor, customer.getId());
        return ResponseEntity.ok(true);
    }

    @Secured({"ANALYST", "CUSTOMER"})
    @GetMapping("/deleteFollowerAnalyst")
    ResponseEntity deleteFollowerAnalyst(@RequestParam("id_analyst") int id_analyst) {
        CustomersEntity customer = customersService.findByLog(
                SecurityContextHolder.getContext().getAuthentication().getName());
        if (customer == null) return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        followerService.deleteFollowersAnalyst(id_analyst, customer.getId());
        return ResponseEntity.ok(true);
    }

    @Secured({"CUSTOMER", "ACTOR", "ANALYST", "STUDIO"})
    @GetMapping("/getPhotoForFollowersActor")
    ResponseEntity getPhotoForFollowersActor(@RequestParam("id_customer") int id_customer) {
        String img = followerService.getForFollowerIMGOfActor(id_customer);
        if (img != null)
            return ResponseEntity.ok(img);
        return (ResponseEntity) ResponseEntity.notFound();
    }

    @Secured({"CUSTOMER", "ACTOR", "ANALYST", "STUDIO"})
    @GetMapping("/getPhotoForFollowersAnalyst")
    ResponseEntity getPhotoForFollowersAnalyst(@RequestParam("id_customer") int id_customer) {
        String img = followerService.getForFollowerIMGOfAnalyst(id_customer);
        if (img != null)
            return ResponseEntity.ok(img);
        return (ResponseEntity) ResponseEntity.notFound();
    }

    @Secured({"CUSTOMER", "ACTOR", "ANALYST", "STUDIO"})
    @GetMapping("/getPhotoForFollowersStudio")
    ResponseEntity getPhotoForFollowersStudio(@RequestParam("id_customer") int id_customer) {
        String img = followerService.getForFollowerIMGOfStudio(id_customer);
        if (img != null)
            return ResponseEntity.ok(img);
        return (ResponseEntity) ResponseEntity.notFound();
    }
}
