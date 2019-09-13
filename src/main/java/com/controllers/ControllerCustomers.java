package com.controllers;

import com.database.CustomersEntity;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.javaMail.EmailClient;
import com.service.CustomersService;
import org.apache.commons.lang.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import java.sql.Date;
import java.util.ArrayList;

@Controller
public class ControllerCustomers {
    private final CustomersService customersService;


    @Autowired
    public ControllerCustomers(CustomersService customersService) {
        this.customersService = customersService;

    }


    //TODO: регистрация пользователя: 2) через ВК
    @Secured("GUEST")
    @RequestMapping(value = "/registrationVK", produces = "text/html;charset=UTF-8", method = RequestMethod.GET)
    ResponseEntity registrationVK(@RequestParam("first_name") String first_name,
                                  @RequestParam("last_name") String last_name,
                                  @RequestParam("herf") String href,
                                  @RequestParam("mid") String mid) {

        customersService.registrationWithSocialNetwork(StringEscapeUtils.unescapeHtml(first_name),
                StringEscapeUtils.unescapeHtml(last_name),
                href, mid);
        return ResponseEntity.ok(true);
    }

    //TODO: регистрация пользователя: 3) через GMail
    @Secured({"GUEST"})
    @GetMapping("/registrationGMAIL")
    ResponseEntity registrationGMAIL(@RequestParam("first_name") String first_name,
                                     @RequestParam("last_name") String last_name,
                                     @RequestParam("email") String email) {
        customersService.registrationWithSocialNetwork(first_name, last_name,
                email, email);
        return ResponseEntity.ok(true);
    }

    @RequestMapping(value = "/newCust", method = RequestMethod.GET)
    String newCust() {
        Date d = new Date(2018, 11, 12);
        customersService.insertNewCustomer("sasha",
                "DFff", "werftrds", "fghj@ff",
                "wedfrgtyhj", "234567", true, d, 1);

        try {
            EmailClient.sendAsHtml("dashkova_m@inbox.ru",
                    "MoviesMT",
                    "<h2>Welcome to our site:)</h2><p>You are registered in MoviesMT!</p>");
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return "connected";
    }


    @Secured({"GUEST"})
    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.OK)
    public void registration(@RequestParam("firstName") String firstName,
                             @RequestParam("lastName") String last_name,
                             @RequestParam("mobile") String mobile,
                             @RequestParam("email") String email,
                             @RequestParam("username") String username,
                             @RequestParam("password") String password) {
        String img = "data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAJUAAACACAYAAAACuStnAAAAAXNSR0IArs4c6QAAAARnQU1BAACxjwv8YQUAAAAJcEhZcwAAEnQAABJ0Ad5mH3gAABVsSURBVHhe7Z0HkBXFE8Yx55yzmBUD5lhGxIBZVMqECRNmzFlUMKNCmSOUCqIoGFAwYo6YE2XCjDln5///bW2/6l32jnc607Pvbr+qKXi3725nd7+d6en+uqedq1DBMypSVfCOilQVvKMiVQXvqEhVwTsqUlXwjopUFbyjIlUd+Oeff9yff/7p/vrrr/QnFZpDRar/44cffnCvv/66e/zxx92gQYPcaaed5o4//njXq1cv17NnT7fvvvu6XXfd1XXv3t0deuih7thjj3UnnHCC6927t7v22mvdqFGj3PPPP+8++OAD99tvv6V/te2izZHql19+cePGjXMjRoxIiLPGGmu4ZZZZxs0999xu5plndlNOOaVr165d3W2KKaZw008/vZt11lndQgst5Dp06OA6derkrrjiCvfII4+4CRMmtLkRrk2Q6ptvvnH333+/22+//dwGG2zgFltsMTfZZJMVksRnm2GGGVzHjh3dFlts4c4880z31ltvuT/++CPtVetFqyUVo8NTTz2VTFPLLrtsMqIUPfh8m2qqqdx8883nllhiCbfqqqu6dddd12200UZus802S0YgPvPz5Zdf3i211FJu4YUXdjPOOGPh38o3RrOuXbu6K6+80n344YdpT1sfWh2pvv32Wzd06NCEBDPNNFPhw5UGGZj6tttuO3fhhRe6G2+80Y0cOdK988477tNPP01GuB9//DGZMhlhfv/99+QzP2da++yzzxJyPPHEE+6WW25x/fv3d126dHFLLrlkMiUWnZMGweeZZ55k5HzsscdanR3WakgFCfr06eNWW201N/XUUxc+TOwlpqPDDjvMXX755e7JJ59MCMPqzhf4Wxj+Dz30kDv33HPd3nvvndhaTU23s8wyi9t4443dkCFDWg25Gp5UjBg8POykooc23XTTJUSDSIwKX3/9dfqbNvj777/dRx99lJB4m222cYssskhhP6eZZppkxGSUbXTDvmFJxVTE8n/llVcufEhzzjlnsvx/+OGH3ffff5/+VlxAMFaerAwhOvZbvt+Mprvttpt75pln0t9qPDQkqcaOHZusqIoeyqKLLupOOumk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";
        java.util.Date d = new java.util.Date();
        Date date = new Date(d.getYear(), d.getMonth(), d.getDay());
        String user = firstName + " " + last_name;
        customersService.insertNewCustomer(user, username, img, email, password, mobile, true, date, 0);
        try {
            EmailClient.sendAsHtml(email,
                    "MoviesMT",
                    "<h2>Welcome to our site:)</h2><p>You are registered in MoviesMT!</p>");
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

//    @RequestMapping(value = "/login", method = RequestMethod.POST)
//    public ResponseEntity auth(@RequestParam("username") String username,
//                               @RequestParam("password") String password) {
//
//        SecurityContextHolder.getContext().setAuthentication(
//                        authenticationManager.authenticate(
//                                new UsernamePasswordAuthenticationToken(username, password)
//                        )
//                );
//
//
//        if (customersService.auth(username, password)) return new ResponseEntity(HttpStatus.OK);
//        return new ResponseEntity(HttpStatus.UNAUTHORIZED);
//    }

    @Secured({"CUSTOMER"})
    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public ResponseEntity getPoints() {
        CustomersEntity customer = customersService.findByLog(
                SecurityContextHolder.getContext().getAuthentication().getName());
        if (customer == null) return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().setPrettyPrinting().create();
        try {
            customer = customersService.findByLog(customer.getLogin());
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        String g = gson.toJson(customer);
        return ResponseEntity.ok(g);

    }

    @Secured({"CUSTOMER", "ACTOR", "ANALYST", "STUDIO"})
    @RequestMapping(value = "/userImgLoad", method = RequestMethod.POST)
    public ResponseEntity imgLoad(@RequestParam("imgUrl") String img) {
        CustomersEntity customer = customersService.findByLog(
                SecurityContextHolder.getContext().getAuthentication().getName());
        if (customer == null) return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        System.out.println("img" + customer.getProfileImageUrl());
        customersService.loadImg(customer.getId(), img);
        return new ResponseEntity(HttpStatus.OK);

    }

    @Secured({"CUSTOMER", "ACTOR", "ANALYST", "STUDIO"})
    @RequestMapping(value = "/changeSet", method = RequestMethod.POST)
    public ResponseEntity dataLoad(@RequestParam("firstName") String firstName,
                                   @RequestParam("lastName") String last_name,
                                   @RequestParam("mobile") String mobile,
                                   @RequestParam("email") String email) {
        CustomersEntity customer = customersService.findByLog(
                SecurityContextHolder.getContext().getAuthentication().getName());
        System.out.println("pidar"+SecurityContextHolder.getContext().getAuthentication().getName());
        if (customer == null) return new ResponseEntity(HttpStatus.UNAUTHORIZED);

        if (email != null) customersService.updateEmail(customer.getId(), email);
        if (mobile != null) customersService.updateMobailNumber(customer.getId(), mobile);

        String f, l;
        String[] name = customer.getName().split(" ");
        if (firstName != null) f = firstName;
        else f = name[0];
        if (last_name != null) l = last_name;
        else l = name[1];
        f = f + " " + l;
        customersService.updateName(f, customer.getId());
        return new ResponseEntity(HttpStatus.OK);

    }

    @Secured({"CUSTOMER", "ACTOR", "ANALYST", "STUDIO"})
    @RequestMapping(value = "/a")
    public ResponseEntity a(){
        return ResponseEntity.ok("pidar" + SecurityContextHolder.getContext().getAuthentication().getName());
    }

    @Secured({"CUSTOMER"})
    @RequestMapping(value = "/subscribe", method = RequestMethod.GET)
    public ResponseEntity getSub() {
        CustomersEntity customer = customersService.findByLog(
                SecurityContextHolder.getContext().getAuthentication().getName());
        if (customer == null) return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        ArrayList<Integer> ids = customersService.subscribeID(customer.getId());
        StringBuilder builder = new StringBuilder();
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().setPrettyPrinting().create();
        for (Integer i : ids
        ) {
            customer = customersService.findById(i);
            String g = gson.toJson(customer);
            builder.append(g);
        }
        System.out.println(builder.toString());
        return ResponseEntity.ok(builder.toString());

    }

//    @Secured({"CUSTOMER", "ACTOR", "ANALYST", "STUDIO"})
//    @RequestMapping(value = "/exit")
//    public ResponseEntity exit() {
//        return ResponseEntity.ok(null);
//    }

    @Secured("STUDIO")
    @GetMapping("/updateLevelAccess")
    ResponseEntity changeLevelAccess(@RequestParam("id_customer") int id_customer) {
        CustomersEntity customer = customersService.findByLog(
                SecurityContextHolder.getContext().getAuthentication().getName());
        if (customer == null) return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        customersService.updateLevelAccess(id_customer, customer.getId());
        return (ResponseEntity) ResponseEntity.ok();
    }

}
