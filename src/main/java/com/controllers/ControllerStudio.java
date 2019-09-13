package com.controllers;

import com.database.CustomersEntity;
import com.service.CustomersService;
import com.service.StudioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ControllerStudio {
    private final StudioService studioService;
    private final CustomersService customersService;

    @Autowired
    public ControllerStudio(StudioService studioService, CustomersService customersService) {
        this.studioService = studioService;
        this.customersService = customersService;
    }

    @Secured("STUDIO")
    @RequestMapping("getPurse")
    public ResponseEntity getPurse(){
        CustomersEntity customer = customersService.findByLog(
                SecurityContextHolder.getContext().getAuthentication().getName());
        if (customer == null) return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        return ResponseEntity.ok("Studio porse" + studioService.getStudioPurse(customer.getId()));
    }

    @Secured("TECHNICAL_SUPPORT")
    @RequestMapping("setPurse")
    public ResponseEntity setPurse(@RequestParam("id_studio") int id_studio,
                                   @RequestParam("purse") long purse){
        CustomersEntity customer = customersService.findByLog(
                SecurityContextHolder.getContext().getAuthentication().getName());
        if (customer.getLevelAccess() != 4) return (ResponseEntity) ResponseEntity.status(HttpStatus.LOCKED);
        studioService.setPurse(id_studio, purse);
        return ResponseEntity.ok("ok!");
    }

    @Secured("STUDIO")
    @RequestMapping(value = "/setInfoStudio",method = RequestMethod.POST)
    public ResponseEntity setInfoStudio(@RequestParam("info") String info){
        CustomersEntity customer = customersService.findByLog(
                SecurityContextHolder.getContext().getAuthentication().getName());
        if (customer == null) return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        studioService.setInfo(customer.getId(), info);
        return ResponseEntity.ok("ok!");
    }

    @Secured({"STUDIO","ACTOR","CUSTOMER","ANALYST"})
    @RequestMapping("/getInfoAboutStudio")
    public ResponseEntity getInfoAboutStudio(@RequestParam("id_studio") int id_studio){
        return ResponseEntity.ok(studioService.getInfo(id_studio));
    }
}
