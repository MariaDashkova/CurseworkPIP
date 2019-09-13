package com.controllers;

import com.database.ComplaintEntity;
import com.database.CustomersEntity;
import com.google.gson.Gson;
import com.service.ComplaintService;
import com.service.CustomersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ControllerComplaint {


    private final ComplaintService complaintService;
    private final CustomersService customersService;

    @Autowired
    public ControllerComplaint(ComplaintService complaintService, CustomersService customersService) {
        this.complaintService = complaintService;
        this.customersService = customersService;
    }

    @Secured({"CUSTOMER", "ACTOR", "ANALYST", "STUDIO"})
    @RequestMapping(value = "/newComplaint", method = RequestMethod.POST)
    public ResponseEntity addTag(@RequestParam("body") String body) {
        CustomersEntity customer = customersService.findByLog(
                SecurityContextHolder.getContext().getAuthentication().getName());
        if (customer == null) return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        complaintService.saveNewComplaint(body, customer.getId());
        return ResponseEntity.ok(true);
    }

    @Secured({"TECHNICAL_SUPPORT"})
    @RequestMapping(value = "/answerForComplaint", method = RequestMethod.POST)
    public ResponseEntity answerForComplaint(@RequestParam("idComplaint") int id,
                                             @RequestParam("body") String body) {
        complaintService.answer(id, body);
        return ResponseEntity.ok(true);
    }

    @Secured({"TECHNICAL_SUPPORT"})
    @RequestMapping(value = "/getAllComplaints")
    public ResponseEntity getAllComplaints() {
        Gson g = new Gson();
        StringBuilder builder = new StringBuilder();
        List<ComplaintEntity> complaints = complaintService.currentComplaint();
        builder.append("[");
        for (ComplaintEntity complaint : complaints) {
            builder.append(g.toJson(complaint));
            builder.append(",");
        }
        builder.append("]");
        return ResponseEntity.ok(builder.toString());
    }

}
