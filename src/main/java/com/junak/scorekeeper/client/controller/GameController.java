package com.junak.scorekeeper.client.controller;

import com.junak.scorekeeper.client.Constants;
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

    @PostMapping("/scoreGame")
    public String scoreGame(@RequestParam("gameId") int gameId,
                            @RequestParam("homeTeamId") int homeTeamId,
                            @RequestParam("visitorTeamId") int visitorTeamId,
                            Model theModel) {
//        Game game = gameService.findById(gameId);
//        List<Player> visitorTeamPlayers = playerService.findAllTeamPlayers(visitorTeamId);
//        Team visitorTeam = teamService.findById(visitorTeamId);
//        List<Player> homeTeamPlayers = playerService.findAllTeamPlayers(homeTeamId);
//        Team homeTeam = teamService.findById(homeTeamId);
//
//        theModel.addAttribute("gameId", gameId);
//        theModel.addAttribute("visitorTeam", visitorTeam);
//        theModel.addAttribute("homeTeam", homeTeam);
//        theModel.addAttribute("visitorTeamPlayers", visitorTeamPlayers);
//        theModel.addAttribute("homeTeamPlayers", homeTeamPlayers);

        Game game = gameService.findById(gameId);

        GameWrapper gameWrapper = wrapGame(game);

        Team attackingTeam = gameWrapper.getVisitorTeam().getAttacking() ?
                gameWrapper.getVisitorTeam() : gameWrapper.getHomeTeam();

        Team defendingTeam = gameWrapper.getVisitorTeam().getAttacking() ?
                gameWrapper.getHomeTeam() : gameWrapper.getVisitorTeam();

        List<Player> attackingTeamPlayers = playerService.findAllTeamPlayers(attackingTeam.getId());
        List<Player> defendingTeamPlayers = playerService.findAllTeamPlayers(defendingTeam.getId());

        Player currentBatter = getCurrentBatter(attackingTeamPlayers);
        Player currentFirstBaseOffence = getCurrentFirstBaseOffence(attackingTeamPlayers);
        Player currentSecondBaseOffence = getCurrentSecondBaseOffence(attackingTeamPlayers);
        Player currentThirdBaseOffence = getCurrentThirdBaseOffence(attackingTeamPlayers);
        Player currentPitcher = getCurrentPitcher(defendingTeamPlayers);
        Player currentCatcher = getCurrentCatcher(defendingTeamPlayers);
        Player currentFirstBaseDefence = getCurrentFirstBaseDefence(defendingTeamPlayers);
        Player currentSecondBaseDefence = getCurrentSecondBaseDefence(defendingTeamPlayers);
        Player currentShortStop = getCurrentShortStop(defendingTeamPlayers);
        Player currentThirdBaseDefence = getThirdBaseDefence(defendingTeamPlayers);
        Player currentRightField = getRightField(defendingTeamPlayers);
        Player currentCenterField = getCenterField(defendingTeamPlayers);
        Player currentLeftField = getLeftField(defendingTeamPlayers);

        theModel.addAttribute("gameWrapper", gameWrapper);
        theModel.addAttribute("batter", currentBatter);
        theModel.addAttribute("currentFirstBaseOffence", currentFirstBaseOffence);
        theModel.addAttribute("currentSecondBaseOffence", currentSecondBaseOffence);
        theModel.addAttribute("currentThirdBaseOffence", currentThirdBaseOffence);
        theModel.addAttribute("currentPitcher", currentPitcher);
        theModel.addAttribute("currentCatcher", currentCatcher);
        theModel.addAttribute("currentFirstBaseDefence", currentFirstBaseDefence);
        theModel.addAttribute("currentSecondBaseDefence", currentSecondBaseDefence);
        theModel.addAttribute("currentShortStop", currentShortStop);
        theModel.addAttribute("currentThirdBaseDefence", currentThirdBaseDefence);
        theModel.addAttribute("currentRightField", currentRightField);
        theModel.addAttribute("currentCenterField", currentCenterField);
        theModel.addAttribute("currentLeftField", currentLeftField);

        return "game-score";
    }

    private Player getCurrentBatter(List<Player> players) {
        for (Player player : players) {
            if (player.getOffencePosition() != null && player.getOffencePosition().equals(Constants.batter)){
                return player;
            }
        }
        return null;
    }

    private Player getCurrentFirstBaseOffence(List<Player> players) {
        for (Player player : players) {
            if (player.getOffencePosition() != null && player.getOffencePosition().equals(Constants.runnerFirstBase)){
                return player;
            }
        }
        return null;
    }

    private Player getCurrentSecondBaseOffence(List<Player> players) {
        for (Player player : players) {
            if (player.getOffencePosition() != null && player.getOffencePosition().equals(Constants.runnerSecondBase)){
                return player;
            }
        }
        return null;
    }

    private Player getCurrentThirdBaseOffence(List<Player> players) {
        for (Player player : players) {
            if (player.getOffencePosition() != null && player.getOffencePosition().equals(Constants.runnerThirdBase)){
                return player;
            }
        }
        return null;
    }

    private Player getCurrentPitcher(List<Player> players) {
        for (Player player : players) {
            if (player.getDefencePosition() != null && player.getDefencePosition().equals(Constants.pitcher)){
                return player;
            }
        }
        return null;
    }

    private Player getCurrentCatcher(List<Player> players) {
        for (Player player : players) {
            if (player.getDefencePosition() != null && player.getDefencePosition().equals(Constants.catcher)){
                return player;
            }
        }
        return null;
    }

    private Player getCurrentFirstBaseDefence(List<Player> players) {
        for (Player player : players) {
            if (player.getDefencePosition() != null && player.getDefencePosition().equals(Constants.defenderFirstBase)){
                return player;
            }
        }
        return null;
    }

    private Player getCurrentSecondBaseDefence(List<Player> players) {
        for (Player player : players) {
            if (player.getDefencePosition() != null && player.getDefencePosition().equals(Constants.defenderSecondBase)){
                return player;
            }
        }
        return null;
    }

    private Player getCurrentShortStop(List<Player> players) {
        for (Player player : players) {
            if (player.getDefencePosition() != null && player.getDefencePosition().equals(Constants.shortStop)){
                return player;
            }
        }
        return null;
    }

    private Player getThirdBaseDefence(List<Player> players) {
        for (Player player : players) {
            if (player.getDefencePosition() != null && player.getDefencePosition().equals(Constants.defenderThirdBase)){
                return player;
            }
        }
        return null;
    }

    private Player getRightField(List<Player> players) {
        for (Player player : players) {
            if (player.getDefencePosition() != null && player.getDefencePosition().equals(Constants.rightField)){
                return player;
            }
        }
        return null;
    }

    private Player getCenterField(List<Player> players) {
        for (Player player : players) {
            if (player.getDefencePosition() != null && player.getDefencePosition().equals(Constants.centerField)){
                return player;
            }
        }
        return null;
    }

    private Player getLeftField(List<Player> players) {
        for (Player player : players) {
            if (player.getDefencePosition() != null && player.getDefencePosition().equals(Constants.leftField)){
                return player;
            }
        }
        return null;
    }

    @PostMapping("/lineup")
    public String showLineup(@RequestParam("gameId") int gameId,
                            Model theModel) {
        return null;
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
