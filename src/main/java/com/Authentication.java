package com;

import com.database.CustomersEntity;
import com.service.CustomersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.stereotype.Component;


import java.util.Collections;


@Component
public class Authentication{

    private final CustomersService customersService;

    @Autowired
    public Authentication(CustomersService customersService) {
        this.customersService = customersService;

    }

    @Bean
    public AuthenticationManager getAuthenticationManager() {

        return authentication -> {
            String role = "";
            if (authentication.getName().isEmpty()) // the user is just a guest
                return new UsernamePasswordAuthenticationToken(authentication.getName(),
                        authentication.getCredentials(),
                        Collections.singletonList(new SimpleGrantedAuthority("GUEST")));

            CustomersEntity customer = customersService.returnUser(authentication.getName(), customersService.getMd5((String) authentication.getCredentials()));
            if (customer == null) throw new BadCredentialsException("Well, you need a password if you're not a guest");

            switch (customer.getLevelAccess()) {
                case 0:
                    role = "CUSTOMER";
                    break;
                case 1:
                    role = "ACTOR";
                    break;
                case 2:
                    role = "ANALYST";
                    break;
                case 3:
                    role = "STUDIO";
                    break;
                case 4:
                    role = "TECHNICAL_SUPPORT";
            }

            return new UsernamePasswordAuthenticationToken(authentication.getName(),
                    authentication.getCredentials(),
                    Collections.singletonList(new SimpleGrantedAuthority(role)));
        };

    }



}