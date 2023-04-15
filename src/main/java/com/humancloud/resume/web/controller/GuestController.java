package com.humancloud.resume.web.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/thor/guest")
public class GuestController {

    @GetMapping("/index")
    public ResponseEntity<String> index(){
        return ResponseEntity.ok("Welcome to guest page");
    }
}
