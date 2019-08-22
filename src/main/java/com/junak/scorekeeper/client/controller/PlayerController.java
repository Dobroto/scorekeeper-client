package com.junak.scorekeeper.client.controller;

import com.junak.scorekeeper.client.model.*;
import com.junak.scorekeeper.client.service.interfaces.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/players")
@SessionAttributes("player")
public class PlayerController {

    @Autowired
    PlayerService playerService;

    @Autowired
    PlayerHittingDetailsService playerHittingDetailsService;

    @Autowired
    PlayerPitchingDetailsService playerPitchingDetailsService;

    @Autowired
    PlayerFieldingDetailsService playerFieldingDetailsService;

    @Autowired
    TeamService teamService;

    @GetMapping("/list")
    public String listAllPlayers(Model theModel) {

        // get players from the service
        List<Player> thePlayers = playerService.findAll();

        // add the players to the model
        theModel.addAttribute("players", thePlayers);

        return "list-players";
    }

    @GetMapping("/team")
    public String listAllTeamPlayers(@RequestParam("teamId") int teamId, Model theModel) {

        // get players from the service
        List<Player> thePlayers = playerService.findAllTeamPlayers(teamId);

        // add the players to the model
        theModel.addAttribute("players", thePlayers);

        return "list-players";
    }

    @GetMapping("/get")
    public String getPlayer(@RequestParam("playerId") int playerId, Model theModel) {

        // get the player from our service
        Player thePlayer = playerService.findById(playerId);

        // set player as a model attribute to pre-populate the form
        theModel.addAttribute("player", thePlayer);

        return "get-player";
    }

    @GetMapping("/showFormForAddPlayer")
    public String showFormForAddPlayer(Model theModel) {

        // create model attribute to bind form data
        Player thePlayer = new Player();

        theModel.addAttribute("player", thePlayer);

        return "player-form";
    }

    @PostMapping("/showFormForUpdatePlayer")
    public String showFormForUpdatePlayer(@RequestParam("playerId") int theId,
                                    Model theModel) {

        // get the player from the service
        Player thePlayer = playerService.findById(theId);

        // set player as a model attribute to pre-populate the form
        theModel.addAttribute("player", thePlayer);

        // send over to our form
        return "player-form";
    }

    @PostMapping("/save")
    public String savePlayer(@ModelAttribute("player") Player thePlayer) {

        // save the player
        playerService.save(thePlayer);

        // use a redirect to prevent duplicate submissions
        return "redirect:/players/list";
    }

    @PostMapping("/delete")
    public String deletePlayer(@RequestParam("playerId") int theId) {

        // delete the customer
        playerService.deleteById(theId);

        return "redirect:/players/list";
    }

    @PostMapping("/showPlayerStats")
    public String showPlayerStats(@RequestParam("playerId") int theId, Model theModel) {

        Player thePlayer = playerService.findById(theId);

        PlayerHittingDetails playerHittingDetails =
                playerHittingDetailsService.findById(thePlayer.getPlayerHittingDetails());

        PlayerPitchingDetails playerPitchingDetails =
                playerPitchingDetailsService.findById(thePlayer.getPlayerPitchingDetails());

        PlayerFieldingDetails playerFieldingDetails =
                playerFieldingDetailsService.findById(thePlayer.getPlayerFieldingDetails());

        Team team = teamService.findById(thePlayer.getTeam());

        theModel.addAttribute("player", thePlayer);
        theModel.addAttribute("team", team);
        theModel.addAttribute("playerHittingDetails", playerHittingDetails);
        theModel.addAttribute("playerPitchingDetails", playerPitchingDetails);
        theModel.addAttribute("playerFieldingDetails", playerFieldingDetails);

        return "player-stats";
    }

}
