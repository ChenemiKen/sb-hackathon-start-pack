package com.chenemiken.sbhackathonstartpack.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class Base {
    @GetMapping(path = "")
    private String base(){
        return "Hello world";
    }
}
