package com.junak.scorekeeper.client.controller;

import com.junak.scorekeeper.client.service.interfaces.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/scorekeeper")
public class HomeController {

    @Autowired
    PlayerService playerService;

    @GetMapping("/homePage")
    public String listAllManagers() {

        return "start";
    }

}
