package com.junak.scorekeeper.client.controller;

import com.junak.scorekeeper.client.model.*;
import com.junak.scorekeeper.client.service.interfaces.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.*;

@Controller
@RequestMapping("/teams")
@SessionAttributes("team")
public class TeamController {

    @Autowired
    TeamService teamService;

    @Autowired
    PlayerService playerService;

    @Autowired
    PlayerHittingDetailsService playerHittingDetailsService;

    @Autowired
    PlayerPitchingDetailsService playerPitchingDetailsService;

    @Autowired
    PlayerFieldingDetailsService playerFieldingDetailsService;

    @GetMapping("/list")
    public String listAllTeams(Model theModel) {

        List<Team> theTeams = teamService.findAll();

        theModel.addAttribute("teams", theTeams);

        return "list-teams";
    }

    @GetMapping("/showFormForAddTeam")
    public String showFormForAddTeam(Model theModel) {

        // create model attribute to bind form data
        Team theTeam = new Team();

        theModel.addAttribute("team", theTeam);

        return "team-form";
    }

    @PostMapping("/showFormForUpdateTeam")
    public String showFormForUpdateTeam(@RequestParam("teamId") int theId,
                                        Model theModel) {

        // get the player from the service
        Team theTeam = teamService.findById(theId);

        // set player as a model attribute to pre-populate the form
        theModel.addAttribute("team", theTeam);

        // send over to our form
        return "team-form";
    }

    @PostMapping("/save")
    public String saveTeam(@ModelAttribute("team") Team theTeam) {

        // save the team
        teamService.save(theTeam);

        // use a redirect to prevent duplicate submissions
        return "redirect:/teams/list";
    }

    @PostMapping("/delete")
    public String deleteTeam(@RequestParam("teamId") int theId) {

        // delete the team
        teamService.deleteById(theId);

        return "redirect:/teams/list";
    }

    @PostMapping("/showTeamStats")
    public String showTeamStats(@RequestParam("teamId") int theId, Model theModel) {

        Team theTeam = teamService.findById(theId);
        List<Player> teamPlayers = playerService.findAllTeamPlayers(theId);

        Map<Player, PlayerHittingDetails> playerHittingDetailsMap = new LinkedHashMap<>();
        Map<Player, PlayerPitchingDetails> playerPitchingDetailsMap = new LinkedHashMap<>();
        Map<Player, PlayerFieldingDetails> playerFieldingDetailsMap = new LinkedHashMap<>();

        for (Player player : teamPlayers) {
            playerHittingDetailsMap.put(player, playerHittingDetailsService.findById(player.getPlayerHittingDetails()));
            playerPitchingDetailsMap.put(player, playerPitchingDetailsService.findById(player.getPlayerPitchingDetails()));
            playerFieldingDetailsMap.put(player, playerFieldingDetailsService.findById(player.getPlayerFieldingDetails()));
        }

        theModel.addAttribute("players", teamPlayers);
        theModel.addAttribute("team", theTeam);
        theModel.addAttribute("playerHittingDetailsMap", playerHittingDetailsMap);
        theModel.addAttribute("playerPitchingDetailsMap", playerPitchingDetailsMap);
        theModel.addAttribute("playerFieldingDetailsMap", playerFieldingDetailsMap);

        return "team-stats";
    }

    @RequestMapping(value = "/showRoster", method = {RequestMethod.POST, RequestMethod.GET})
    public String showRoster(@RequestParam("teamId") int theId, Model theModel) {

        Team theTeam = teamService.findById(theId);
        List<Player> teamPlayers = playerService.findAllTeamPlayers(theId);
        boolean isLineup = false;

        theModel.addAttribute("players", teamPlayers);
        theModel.addAttribute("team", theTeam);
        theModel.addAttribute("isLineup", isLineup);

        return "team-roster";
    }

    @PostMapping("/removeFromTeam")
    public String removeFromTeam(@RequestParam("playerId") int playerId,
                                 @RequestParam("teamId") int teamId, RedirectAttributes redirectAttributes) {

        Player player = playerService.findById(playerId);
        player.setTeam(-1);
        playerService.save(player);

        redirectAttributes.addAttribute("teamId", teamId);
        return "redirect:/teams/showRoster";
    }
}
