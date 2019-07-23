package com.junak.scorekeeper.client.controller;

import com.junak.scorekeeper.client.model.Player;
import com.junak.scorekeeper.client.service.interfaces.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/player")
public class PlayerController {

    @Autowired
    PlayerService playerService;

    @GetMapping("/list")
    public String listPlayers(Model theModel) {

        // get customers from the service
        List<Player> thePlayers = playerService.findAll();

        // add the customers to the model
        theModel.addAttribute("players", thePlayers);

        return "list-players";
    }

}
