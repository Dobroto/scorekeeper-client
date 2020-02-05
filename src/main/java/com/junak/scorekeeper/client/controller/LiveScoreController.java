package com.junak.scorekeeper.client.controller;

import com.junak.scorekeeper.client.model.Play;
import com.junak.scorekeeper.client.model.Player;
import com.junak.scorekeeper.client.service.interfaces.GameService;
import com.junak.scorekeeper.client.service.interfaces.PlayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/liveScore")
public class LiveScoreController {

    @Autowired
    PlayService playService;

    @Autowired
    GameService gameService;

    @PostMapping("/game")
    public String showEvents(@RequestParam("gameId") int gameId, Model theModel) {

        List<Play> plays = playService.getAllPlaysInGame(gameId);

        theModel.addAttribute("plays", plays);
        theModel.addAttribute("gameId", gameId);
        return "livescore";
    }


}
