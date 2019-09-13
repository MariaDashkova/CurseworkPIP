package com.controllers;

import com.database.CustomersEntity;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.service.AnalystService;
import com.service.CustomersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.TreeMap;

@RestController
public class ControllerAnalyst {
    private final AnalystService analystService;
    private final CustomersService customersService;
    final char dm = (char) 34;
    private CustomersEntity analyst = null;

    @Autowired
    public ControllerAnalyst(AnalystService analystService, CustomersService customersService) {
        this.analystService = analystService;
        this.customersService = customersService;
    }

    @Secured({"CUSTOMER", "ACTOR", "ANALYST", "STUDIO"})
    @RequestMapping(value = "/allReviewsByAnalystId", method = RequestMethod.GET)
    ResponseEntity allReviewsByAnalystId(@RequestParam("id") int id){
        TreeMap<String,String> filmRev = analystService.allReviewsByAnalystId(id);
        return ResponseEntity.ok(String.valueOf(filmRev.firstEntry()));
    }

    @Secured({"CUSTOMER", "ACTOR", "ANALYST", "STUDIO"})
    @RequestMapping(value = "/getAnalystInfo", method = RequestMethod.GET)
    public ResponseEntity getInfo(@RequestParam("id") int id) {
        return ResponseEntity.ok(analystService.analystInfo(id));
    }

    @Secured({"CUSTOMER", "ACTOR", "ANALYST", "STUDIO"})
    @RequestMapping(value = "/analystScore", method = RequestMethod.GET)
    public ResponseEntity getAnalystScore(@RequestParam("id") int id) {
        return ResponseEntity.ok(analystService.analystScore(id));
    }

    @Secured({"ANALYST"})
    @RequestMapping(value = "/newReview", method = RequestMethod.POST)
    public ResponseEntity add(@RequestParam("idFilm") int idFilm,
                      @RequestParam("body") String body){
        CustomersEntity customer = customersService.findByPas(
                SecurityContextHolder.getContext().getAuthentication().getCredentials().toString());
        if (customer == null) return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        try{
        analystService.addNewReview(customer.getId(),idFilm,body);
            return ResponseEntity.ok(true);
        }catch (NullPointerException e){
            return (ResponseEntity) ResponseEntity.notFound();
        }
    }

    @Secured({"CUSTOMER", "ACTOR", "ANALYST", "STUDIO"})
    @RequestMapping(value = "/analystReview", method = RequestMethod.GET)
    public ResponseEntity getSub() {
        CustomersEntity analyst = customersService.findByPas(
                SecurityContextHolder.getContext().getAuthentication().getCredentials().toString());
        if (analyst == null) return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        TreeMap<String, String> filmsRev = analystService.allReviewsByAnalystId(analyst.getId());
        StringBuilder builder = new StringBuilder();
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().setPrettyPrinting().create();
        builder.append("[");

        for(Map.Entry e : filmsRev.entrySet()){
            builder.append("{").append(dm).append("name").append(dm);
            builder.append(":").append(dm).append(e.getKey()).append(dm);
            builder.append(",").append(dm).append("body").append(dm);
            builder.append(":").append(dm).append(e.getValue()).append(dm).append("},");
        }

        builder.setLength(builder.length() -1);
        builder.append("]");
        return ResponseEntity.ok(builder.toString());
    }

    @Secured({"CUSTOMER", "ACTOR", "ANALYST", "STUDIO"})
    @RequestMapping(value = "/analystScore")
    public ResponseEntity analystScore(@RequestParam("id") int id){
        return ResponseEntity.ok(analystService.analystScore(id));
    }
}
