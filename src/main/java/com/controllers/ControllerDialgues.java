package com.controllers;

import com.database.CustomersEntity;
import com.service.CustomersService;
import com.service.DialogueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
public class ControllerDialgues {
    private final DialogueService dialogueService;
    private final CustomersService customersService;

    @Autowired
    public ControllerDialgues(DialogueService dialogueService, CustomersService customersService) {
        this.dialogueService = dialogueService;
        this.customersService = customersService;
    }

    @Secured("ANALYST")
    @GetMapping("/dialogsForAnalyst")
    ResponseEntity dialogsForAnalyst() {
        CustomersEntity customer = customersService.findByLog(
                SecurityContextHolder.getContext().getAuthentication().getName());
        if (customer != null) return ResponseEntity.ok(dialogueService.analystDialogs(customer.getId()));
        else return (ResponseEntity) ResponseEntity.notFound();
    }

    @Secured("STUDIO")
    @GetMapping("/dialogsForStudio")
    ResponseEntity dialogsForStudio() {
        CustomersEntity customer = customersService.findByLog(
                SecurityContextHolder.getContext().getAuthentication().getName());
        if (customer != null) return ResponseEntity.ok(dialogueService.studioDialogs(customer.getId()));
        else return (ResponseEntity) ResponseEntity.notFound();
    }

    @Secured({"STUDIO", "ANALYST"})
    @GetMapping("/getMessagesFromDialogue")
    ResponseEntity getMessagesFromDialogue(@RequestParam("id_dialogue") int id_dialogue) {
        CustomersEntity customer = customersService.findByLog(
                SecurityContextHolder.getContext().getAuthentication().getName());
        if (customer == null) return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        if (dialogueService.check(customer.getId(), id_dialogue))
            return ResponseEntity.ok(dialogueService.messageFromDiaogue(id_dialogue));
        else return (ResponseEntity) ResponseEntity.notFound();
    }

    @Secured("STUDIO")
    @RequestMapping(value = "/setMessage", method = RequestMethod.POST)
    ResponseEntity setMessage(@RequestParam("id_analyst") int id_analyst,
                              @RequestParam("message") String message) {
        CustomersEntity customer = customersService.findByLog(
                SecurityContextHolder.getContext().getAuthentication().getName());
        if (customer == null) return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        dialogueService.setMessages(customer.getId(), id_analyst, message);
        return ResponseEntity.ok(true);
    }

}
