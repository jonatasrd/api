package com.restaurante.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @Autowired
    private UserDetailsService userService;

    @GetMapping("/")
    public String greeting(Authentication authentication) {
        return "Autenticacaoo em memoria";
    }

}
