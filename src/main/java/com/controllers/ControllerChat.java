package com.controllers;

import com.database.CustomersEntity;
import com.service.ChatService;
import com.service.CustomersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
public class ControllerChat {

    private final ChatService chatService;
    private final CustomersService customersService;

    @Autowired
    public ControllerChat(ChatService chatService, CustomersService customersService) {
        this.chatService = chatService;
        this.customersService = customersService;
    }

    @Secured({"CUSTOMER"})
    @RequestMapping(value = "/setMes", method = RequestMethod.POST)
    ResponseEntity setMes(@RequestParam("film_id") int film_id,
                          @RequestParam("message") String message) {
        CustomersEntity customer = customersService.findByLog(
                SecurityContextHolder.getContext().getAuthentication().getName());
        if (customer == null) return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        return ResponseEntity.ok(chatService.returnSetMes(film_id, customer.getId(), message));
    }

    @Secured({"CUSTOMER"})
    @GetMapping(value = "/getMes", produces = "application/json;charset=UTF-8")
    ResponseEntity getMes(@RequestParam("id_film") int id_film) {
        return ResponseEntity.ok(chatService.returnMessageContent(id_film));
    }

//    @GetMapping("/addUserInChat")
//    Boolean addUser() {
//        return chatService.checkUserInChat();
//    }
//
//    @GetMapping("/addChatToUser")
//    String addUserToChat() {
//        return chatService.addNewChat();
//    }
//
//    @GetMapping("/getAllChatsForUser")
//    String getAllChatsForUser() {
//        return chatService.findAllChatsForUser();
//    }
}