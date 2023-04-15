package com.humancloud.resume.web.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping("/thor/swagger-ui")
    public String getRedirectUrl() {
        return "redirect:swagger-ui/index.html#/";
    }
    @GetMapping("/thor/v3/api-docs/")
    public String getAPIDocsRedirectUrl() {
        return "redirect:v3/api-docs/swagger-config/";
    }
}
