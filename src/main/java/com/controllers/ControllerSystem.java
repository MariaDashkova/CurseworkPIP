package com.controllers;

import com.database.CustomersEntity;
import com.service.CustomersService;
import com.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;


@RestController
public class ControllerSystem {

    private final SystemService systemService;
    private final CustomersService customersService;

    @Autowired
    public ControllerSystem(SystemService systemService, CustomersService customersService) {
        this.systemService = systemService;
        this.customersService = customersService;
    }

    @Secured("GUEST")
    @GetMapping("/info")
    ResponseEntity info() {
        String info = systemService.returnInfo();
        if (info != null)
            return ResponseEntity.ok(info);
        else return (ResponseEntity) ResponseEntity.notFound();
    }

    @Secured({"STUDIO"})
    @RequestMapping(value = "/money",method = RequestMethod.POST)
    ResponseEntity money(@RequestParam("money") long money,
                         @RequestParam("id_analyst") int id_analyst) {
        CustomersEntity customer = customersService.findByPas(
                SecurityContextHolder.getContext().getAuthentication().getCredentials().toString());
        if (customer == null) return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        String answer = systemService.moneyTransfer(money, customer.getId(), id_analyst);
        if (answer != null)
            return ResponseEntity.ok(answer);
        else return (ResponseEntity) ResponseEntity.notFound();
    }

}