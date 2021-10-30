package br.com.fiap.wallet.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping("/user")
    public String helloUser(){
        return "Hello user";
    }

    @GetMapping("/admin")
    public String helloAdmin(){
        return "Hello admin";
    }
}
