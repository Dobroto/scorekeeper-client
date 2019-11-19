package com.junak.scorekeeper.client.controller;

import com.junak.scorekeeper.client.Constants;
import com.junak.scorekeeper.client.model.*;
import com.junak.scorekeeper.client.service.interfaces.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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

    @RequestMapping(value = "/list", method = {RequestMethod.POST, RequestMethod.GET})
    public String listAllPlayers(@RequestParam("teamId") int teamId,
                                 @RequestParam("isTeamPlayer") boolean isTeamPlayer,
                                 Model theModel) {

        // get players from the service
        List<Player> thePlayers = playerService.findAll();

        // add the players to the model
        theModel.addAttribute("players", thePlayers);
        theModel.addAttribute("teamId", teamId);
        theModel.addAttribute("isTeamPlayer", isTeamPlayer);

        return "list-players";
    }

    @GetMapping("/team")
    public String listAllTeamPlayers(@RequestParam("teamId") int teamId,
                                     Model theModel) {

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

    @PostMapping("/showFormForAddPlayer")
    public String showFormForAddPlayer(@RequestParam("teamId") int teamId,
                                       Model theModel) {

        // create model attribute to bind form data
        Player thePlayer = new Player();

        theModel.addAttribute("player", thePlayer);
        theModel.addAttribute("teamId", teamId);

        return "player-form";
    }

    @PostMapping("/showFormForUpdatePlayer")
    public String showFormForUpdatePlayer(@RequestParam("playerId") int playerId,
                                          @RequestParam("teamId") int teamId,
                                          @RequestParam("isTeamPlayer") boolean isTeamPlayer,
                                          Model theModel) {

        // get the player from the service
        Player thePlayer = playerService.findById(playerId);

        // set player as a model attribute to pre-populate the form
        theModel.addAttribute("player", thePlayer);
        theModel.addAttribute("teamId", teamId);
        theModel.addAttribute("defendingPositions", Constants.defendingPositions);

        // send over to our form
        if (isTeamPlayer) {
            return "teamPlayer-form";
        }
        return "player-form";
    }

    @PostMapping("/save")
    public String savePlayer(@ModelAttribute("player") Player thePlayer,
                             @RequestParam("isTeamPlayer") boolean isTeamPlayer,
                             @RequestParam("teamId") int teamId, RedirectAttributes redirectAttributes) {

        // save the player
        playerService.save(thePlayer);
        redirectAttributes.addAttribute("teamId", teamId);
        redirectAttributes.addAttribute("isTeamPlayer", isTeamPlayer);

        // use a redirect to prevent duplicate submissions
        if (isTeamPlayer) {
            return "redirect:/teams/showRoster";
        }

        return "redirect:/players/list";
    }

    @PostMapping("/delete")
    public String deletePlayer(@RequestParam("playerId") int theId,
                               @RequestParam("teamId") int teamId,
                               @RequestParam("isTeamPlayer") boolean isTeamPlayer,
                               RedirectAttributes redirectAttributes) {
        // delete the player
        playerService.deleteById(theId);

        redirectAttributes.addAttribute("teamId", teamId);
        redirectAttributes.addAttribute("isTeamPlayer", isTeamPlayer);

        return "redirect:/players/list";
    }

    @PostMapping("/showPlayerStats")
    public String showPlayerStats(@RequestParam("playerId") int theId,
                                  @RequestParam("teamId") int teamId,
                                  @RequestParam("isTeamPlayer") boolean isTeamPlayer,
                                  Model theModel) {

        Player thePlayer = playerService.findById(theId);

        PlayerHittingDetails playerHittingDetails =
                playerHittingDetailsService.findById(thePlayer.getPlayerHittingDetails());

        PlayerPitchingDetails playerPitchingDetails =
                playerPitchingDetailsService.findById(thePlayer.getPlayerPitchingDetails());

        PlayerFieldingDetails playerFieldingDetails =
                playerFieldingDetailsService.findById(thePlayer.getPlayerFieldingDetails());

        Team team = new Team();
        if (thePlayer.getTeam() != 0) {
            team = teamService.findById(thePlayer.getTeam());
        }

        theModel.addAttribute("player", thePlayer);
        theModel.addAttribute("team", team);
        theModel.addAttribute("teamId", teamId);
        theModel.addAttribute("playerHittingDetails", playerHittingDetails);
        theModel.addAttribute("playerPitchingDetails", playerPitchingDetails);
        theModel.addAttribute("playerFieldingDetails", playerFieldingDetails);
        theModel.addAttribute("isTeamPlayer", isTeamPlayer);

        return "player-stats";
    }

}
