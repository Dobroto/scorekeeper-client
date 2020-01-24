package com.junak.scorekeeper.client.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/scorekeeper")
public class HomeController {

    @GetMapping("/homePage")
    public String listAllManagers() {
        return "start";
    }
}
