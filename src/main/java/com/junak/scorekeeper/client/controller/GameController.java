package com.junak.scorekeeper.client.controller;

import com.junak.scorekeeper.client.model.*;
import com.junak.scorekeeper.client.model.wrapper.GameWrapper;
import com.junak.scorekeeper.client.service.interfaces.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.*;

@Controller
@RequestMapping("/games")
@SessionAttributes("game")
public class GameController {

    @Autowired
    GameService gameService;

    @Autowired
    TeamService teamService;

    @Autowired
    FinalResultService finalResultService;

    @Autowired
    InningService inningService;

    @Autowired
    GameHittingDetailsService gameHittingDetailsService;

    @Autowired
    GamePitchingDetailsService gamePitchingDetailsService;

    @Autowired
    GameFieldingDetailsService gameFieldingDetailsService;

    @Autowired
    PlayerService playerService;

    @GetMapping("/list")
    public String listAllGames(Model theModel) {

        // get games from the service
        List<Game> games = gameService.findAll();
        List<GameWrapper> gameWrappers = new ArrayList<>();

        for (Game game : games) {
            gameWrappers.add(wrapGame(game));
        }

        //new Game for adding new game (showFormForAddGame)
        Game game = new Game();

        theModel.addAttribute("game", game);
        theModel.addAttribute("gameWrappers", gameWrappers);
        return "list-games";
    }

    @GetMapping("/showFormForAddGame")
    public String showFormForAddGame(@ModelAttribute("game") Game game, Model theModel) {

        if (game.getVisitorTeam() != 0) {
            Team visitor = teamService.findById(game.getVisitorTeam());
            theModel.addAttribute("visitorName", visitor.getTeamNameLong());
        }
        if (game.getHomeTeam() != 0) {
            Team homeTeam = teamService.findById(game.getHomeTeam());
            theModel.addAttribute("homeName", homeTeam.getTeamNameLong());
        }
        theModel.addAttribute("game", game);

        return "game-form";
    }

    @PostMapping("/showTeamList")
    public String showTeamList(@ModelAttribute("game") Game game,
                               @RequestParam("isVisitor") boolean isVisitor, Model theModel) {
        List<Team> teams = teamService.findAll();

        theModel.addAttribute("game", game);
        theModel.addAttribute("teams", teams);
        theModel.addAttribute("isVisitor", isVisitor);

        return "list-game-teams";
    }

    @PostMapping("/team/choose")
    public String chooseTeam(@ModelAttribute("game") Game game,
                             @RequestParam("teamId") int teamId,
                             @RequestParam("isVisitor") boolean isVisitor,
                             RedirectAttributes redirectAttributes) {

        if (isVisitor) {
            game.setVisitorTeam(teamId);
        } else {
            game.setHomeTeam(teamId);
        }

        redirectAttributes.addFlashAttribute("game", game);
        return "redirect:/games/showFormForAddGame";

    }

    @PostMapping("/addNewGame")
    public String addNewGame(@ModelAttribute("game") Game game) {
        gameService.save(game);

        return "redirect:/games/list";
    }

    @RequestMapping(value = "/showGameInfo", method = {RequestMethod.POST, RequestMethod.GET})
    public String showGameInfo(@RequestParam("gameId") int gameId,
                               Model theModel) {

        Game game = gameService.findById(gameId);

        GameWrapper gameWrapper = wrapGame(game);

        theModel.addAttribute("gameWrapper", gameWrapper);

        return "game-info";
    }

    @PostMapping("/showGameHittingDetails")
    public String showGameHittingDetails(@RequestParam("gameId") int gameId,
                                         @RequestParam("teamId") int teamId,
                                         Model theModel) {
        Game game = gameService.findById(gameId);
        List<Player> teamPlayers = playerService.findAllTeamPlayers(teamId);
        Team team = teamService.findById(teamId);

        Map<Player, GameHittingDetails> gameHittingDetailsMap = new LinkedHashMap<>();

        for (Player player : teamPlayers) {
            gameHittingDetailsMap.put(player, gameHittingDetailsService.getGameHittingDetails(player, game));
        }

        theModel.addAttribute("gameId", gameId);
        theModel.addAttribute("team", team);
        theModel.addAttribute("gameHittingDetailsMap", gameHittingDetailsMap);

        return "game-hitting";
    }

    @PostMapping("/showGamePitchingDetails")
    public String showGamePitchingDetails(@RequestParam("gameId") int gameId,
                                          @RequestParam("teamId") int teamId,
                                          Model theModel) {
        Game game = gameService.findById(gameId);
        List<Player> teamPlayers = playerService.findAllTeamPlayers(teamId);
        Team team = teamService.findById(teamId);

        Map<Player, GamePitchingDetails> gamePitchingDetailsMap = new LinkedHashMap<>();

        for (Player player : teamPlayers) {
            gamePitchingDetailsMap.put(player, gamePitchingDetailsService.getGamePitchingDetails(player, game));
        }

        theModel.addAttribute("gameId", gameId);
        theModel.addAttribute("team", team);
        theModel.addAttribute("gamePitchingDetailsMap", gamePitchingDetailsMap);

        return "game-pitching";
    }

    @PostMapping("/showGameFieldingDetails")
    public String showGameFieldingDetails(@RequestParam("gameId") int gameId,
                                          @RequestParam("teamId") int teamId,
                                          Model theModel) {
        Game game = gameService.findById(gameId);
        List<Player> teamPlayers = playerService.findAllTeamPlayers(teamId);
        Team team = teamService.findById(teamId);

        Map<Player, GameFieldingDetails> gameFieldingDetailsMap = new LinkedHashMap<>();

        for (Player player : teamPlayers) {
            gameFieldingDetailsMap.put(player, gameFieldingDetailsService.getGameFieldingDetails(player, game));
        }

        theModel.addAttribute("gameId", gameId);
        theModel.addAttribute("team", team);
        theModel.addAttribute("gameFieldingDetailsMap", gameFieldingDetailsMap);

        return "game-fielding";
    }

    @PostMapping("/showWinPitcher")
    public String showWinPitcher(@RequestParam("gameId") int gameId,
                                 Model theModel) {
        Game game = gameService.findById(gameId);
        FinalResult finalResult = finalResultService.findById(game.getFinalResult());
        Team winningTeam;
        if (finalResult.getHomeTeamScore() >= finalResult.getVisitorTeamScore()) {
            winningTeam = teamService.findById(game.getHomeTeam());
        } else {
            winningTeam = teamService.findById(game.getVisitorTeam());
        }

        List<Player> players = playerService.findAllTeamPlayers(winningTeam.getId());
        List<Player> pitchers = new ArrayList<>();
        for (Player player : players) {
            if (player.isWasPitcher()) {
                pitchers.add(player);
            }
        }

        theModel.addAttribute("pitchers", pitchers);
        theModel.addAttribute("game", game);

        return "game-win-pitcher";
    }

    @PostMapping("/showLosePitcher")
    public String showLosePitcher(@RequestParam("gameId") int gameId,
                                  Model theModel) {
        Game game = gameService.findById(gameId);
        FinalResult finalResult = finalResultService.findById(game.getFinalResult());
        Team losingTeam;
        if (finalResult.getHomeTeamScore() >= finalResult.getVisitorTeamScore()) {
            losingTeam = teamService.findById(game.getVisitorTeam());
        } else {
            losingTeam = teamService.findById(game.getHomeTeam());
        }

        List<Player> players = playerService.findAllTeamPlayers(losingTeam.getId());
        List<Player> pitchers = new ArrayList<>();
        for (Player player : players) {
            if (player.isWasPitcher()) {
                pitchers.add(player);
            }
        }

        theModel.addAttribute("pitchers", pitchers);
        theModel.addAttribute("game", game);

        return "game-lose-pitcher";
    }

    @PostMapping("/showSavePitcher")
    public String showSavePitcher(@RequestParam("gameId") int gameId,
                                  Model theModel) {
        Game game = gameService.findById(gameId);
        FinalResult finalResult = finalResultService.findById(game.getFinalResult());
        Team winningTeam;
        if (finalResult.getHomeTeamScore() >= finalResult.getVisitorTeamScore()) {
            winningTeam = teamService.findById(game.getHomeTeam());
        } else {
            winningTeam = teamService.findById(game.getVisitorTeam());
        }

        List<Player> players = playerService.findAllTeamPlayers(winningTeam.getId());
        List<Player> pitchers = new ArrayList<>();
        for (Player player : players) {
            if (player.isWasPitcher()) {
                pitchers.add(player);
            }
        }

        theModel.addAttribute("pitchers", pitchers);
        theModel.addAttribute("game", game);

        return "game-save-pitcher";
    }

    @PostMapping("/showBlownSavePitcher")
    public String showBlownSavePitcher(@RequestParam("gameId") int gameId,
                                       Model theModel) {
        Game game = gameService.findById(gameId);
        Team homeTeam = teamService.findById(game.getHomeTeam());
        Team visitorTeam = teamService.findById(game.getVisitorTeam());

        List<Player> homeTeamPlayers = playerService.findAllTeamPlayers(homeTeam.getId());
        List<Player> visitorTeamPlayers = playerService.findAllTeamPlayers(visitorTeam.getId());
        List<Player> players = new ArrayList<>(homeTeamPlayers);
        players.addAll(visitorTeamPlayers);

        List<Player> pitchers = new ArrayList<>();
        for (Player player : players) {
            if (player.isWasPitcher()) {
                pitchers.add(player);
            }
        }

        theModel.addAttribute("pitchers", pitchers);
        theModel.addAttribute("game", game);

        return "game-blown-save-pitcher";
    }

    @PostMapping("/showHoldPitcher")
    public String showHoldPitcher(@RequestParam("gameId") int gameId,
                                  Model theModel) {
        Game game = gameService.findById(gameId);
        Team homeTeam = teamService.findById(game.getHomeTeam());
        Team visitorTeam = teamService.findById(game.getVisitorTeam());

        List<Player> homeTeamPlayers = playerService.findAllTeamPlayers(homeTeam.getId());
        List<Player> visitorTeamPlayers = playerService.findAllTeamPlayers(visitorTeam.getId());
        List<Player> players = new ArrayList<>(homeTeamPlayers);
        players.addAll(visitorTeamPlayers);

        List<Player> pitchers = new ArrayList<>();
        for (Player player : players) {
            if (player.isWasPitcher()) {
                pitchers.add(player);
            }
        }

        theModel.addAttribute("pitchers", pitchers);
        theModel.addAttribute("game", game);

        return "game-hold-pitcher";
    }

    @PostMapping("/pitcher/save")
    public String savePitcher(@ModelAttribute("game") Game game, RedirectAttributes redirectAttributes) {

        // save the player
        gameService.save(game);

        redirectAttributes.addAttribute("gameId", game.getId());
        return "redirect:/games/showGameInfo";
    }

    private GameWrapper wrapGame(Game game) {
        GameWrapper gameWrapper = new GameWrapper();

        gameWrapper.setId(game.getId());
        gameWrapper.setScheduledTime(game.getScheduledTime());
        gameWrapper.setStartTimeOfGame(game.getStartTimeOfGame());
        gameWrapper.setEndTimeOfGame(game.getEndTimeOfGame());
        if (game.getHomeTeam() != 0) {
            gameWrapper.setHomeTeam(teamService.findById(game.getHomeTeam()));
        }
        if (game.getVisitorTeam() != 0) {
            gameWrapper.setVisitorTeam(teamService.findById(game.getVisitorTeam()));
        }
        if (game.getFinalResult() != 0) {
            gameWrapper.setFinalResult(finalResultService.findById(game.getFinalResult()));
        }
        Inning currentInning = inningService.getCurrentInning(game);
        if (currentInning != null) {
            gameWrapper.setCurrentInning(currentInning);
        }
        if ((game.getInnings() != null) && (game.getInnings().size() > 0)) {
            gameWrapper.setInnings(inningService.getInningsList(game));
        }
        if (game.getWinPitcher() != 0) {
            gameWrapper.setWinPitcher(playerService.findById(game.getWinPitcher()));
        }
        if (game.getLosePitcher() != 0) {
            gameWrapper.setLosePitcher(playerService.findById(game.getLosePitcher()));
        }
        if (game.getSavePitcher() != 0) {
            gameWrapper.setSavePitcher(playerService.findById(game.getSavePitcher()));
        }
        if (game.getBlownSavePitcher() != 0) {
            gameWrapper.setBlownSavePitcher(playerService.findById(game.getBlownSavePitcher()));
        }
        if (game.getHoldPitcher() != 0) {
            gameWrapper.setHoldPitcher(playerService.findById(game.getHoldPitcher()));
        }

        return gameWrapper;
    }

}
