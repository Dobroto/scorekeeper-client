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
    public String showTeamList(@RequestParam("isVisitor") boolean isVisitor,
                               @ModelAttribute("game") Game game, Model theModel) {
        List<Team> teams = teamService.findAll();

        theModel.addAttribute("game", game);
        theModel.addAttribute("teams", teams);
        theModel.addAttribute("isVisitor", isVisitor);

        return "list-game-teams";
    }

    @PostMapping("/team/choose")
    public String chooseTeam(@RequestParam("teamId") int teamId,
                             @RequestParam("isVisitor") boolean isVisitor,
                             @ModelAttribute("game") Game game,
                             RedirectAttributes redirectAttributes) {

        if (isVisitor) {
            game.setVisitorTeam(teamId);
        } else {
            game.setHomeTeam(teamId);
        }

        redirectAttributes.addFlashAttribute("game", game);
        return "redirect:/games/showFormForAddGame";

    }

    @PostMapping("/createNewGame")
    public String createNewGame(@ModelAttribute("game") Game game) {
        gameService.createNewGame(game.getHomeTeam(), game.getVisitorTeam());

        return "redirect:/games/list";
    }

    @PostMapping("/deleteGame")
    public String deleteGame(@RequestParam("gameId") int gameId) {
        gameService.deleteGame(gameId);

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
            GameHittingDetails gameHittingDetails = gameHittingDetailsService.getGameHittingDetails(player, game);
            if (gameHittingDetails.getId() != 0) {
                gameHittingDetailsMap.put(player, gameHittingDetails);
            }
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
            GamePitchingDetails gamePitchingDetails = gamePitchingDetailsService.getGamePitchingDetails(player, game);
            if (gamePitchingDetails.getId() != 0) {
                gamePitchingDetailsMap.put(player, gamePitchingDetails);
            }
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
            GameFieldingDetails gameFieldingDetails = gameFieldingDetailsService.getGameFieldingDetails(player, game);
            if (gameFieldingDetails.getId() != 0) {
                gameFieldingDetailsMap.put(player, gameFieldingDetails);
            }
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

    @RequestMapping(value = "/scoreGame", method = {RequestMethod.POST, RequestMethod.GET})
    public String scoreGame(@RequestParam("gameId") int gameId,
                            Model theModel) {

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
        theModel.addAttribute("firstBaseOffence", currentFirstBaseOffence);
        theModel.addAttribute("secondBaseOffence", currentSecondBaseOffence);
        theModel.addAttribute("thirdBaseOffence", currentThirdBaseOffence);
        theModel.addAttribute("pitcher", currentPitcher);
        theModel.addAttribute("catcher", currentCatcher);
        theModel.addAttribute("firstBaseDefence", currentFirstBaseDefence);
        theModel.addAttribute("secondBaseDefence", currentSecondBaseDefence);
        theModel.addAttribute("shortStop", currentShortStop);
        theModel.addAttribute("thirdBaseDefence", currentThirdBaseDefence);
        theModel.addAttribute("rightField", currentRightField);
        theModel.addAttribute("centerField", currentCenterField);
        theModel.addAttribute("leftField", currentLeftField);

        return "game-score";
    }

    @GetMapping("/lineup")
    public String lineup(@RequestParam("teamId") int teamId,
                         @RequestParam("gameId") int gameId,
                         Model theModel) {
        Team theTeam = teamService.findById(teamId);
        List<Player> teamPlayers = playerService.findAllTeamPlayers(teamId);
        boolean isLineup = true;

        theModel.addAttribute("players", teamPlayers);
        theModel.addAttribute("team", theTeam);
        theModel.addAttribute("isLineup", isLineup);
        theModel.addAttribute("gameId", gameId);

        return "team-roster";
    }

    @PostMapping("/play")
    public String startGame(@RequestParam("gameId") int gameId,
                            RedirectAttributes redirectAttributes) {
        gameService.startGame(gameId);

        redirectAttributes.addAttribute("gameId", gameId);
        return "redirect:/games/scoreGame";

    }

    @PostMapping("/ball")
    public String ball(@RequestParam("gameId") int gameId,
                       @RequestParam("pitcherId") int pitcherId,
                       @RequestParam("batterId") int batterId,
                       RedirectAttributes redirectAttributes) {
        gameService.ball(gameId, pitcherId, batterId);

        redirectAttributes.addAttribute("gameId", gameId);
        return "redirect:/games/scoreGame";
    }

    @PostMapping("/strike")
    public String strike(@RequestParam("gameId") int gameId,
                         @RequestParam("pitcherId") int pitcherId,
                         @RequestParam("batterId") int batterId,
                         @RequestParam("strikeCount") int strikeCount,
                         Model theModel,
                         RedirectAttributes redirectAttributes) {
        if (strikeCount >= 2) {
            theModel.addAttribute("gameId", gameId);
            theModel.addAttribute("pitcherId", pitcherId);
            theModel.addAttribute("batterId", batterId);

            return "game-strikeout";
        } else {
            gameService.strike(pitcherId, batterId);

            redirectAttributes.addAttribute("gameId", gameId);
            return "redirect:/games/scoreGame";
        }
    }

    @PostMapping("/strikeoutLooking")
    public String strikeoutLooking(@RequestParam("gameId") int gameId,
                                   @RequestParam("pitcherId") int pitcherId,
                                   @RequestParam("batterId") int batterId,
                                   RedirectAttributes redirectAttributes) {
        gameService.strikeoutLooking(gameId, pitcherId, batterId);

        redirectAttributes.addAttribute("gameId", gameId);
        return "redirect:/games/scoreGame";
    }

    @PostMapping("/strikeoutSwinging")
    public String strikeoutSwinging(@RequestParam("gameId") int gameId,
                                    @RequestParam("pitcherId") int pitcherId,
                                    @RequestParam("batterId") int batterId,
                                    RedirectAttributes redirectAttributes) {
        gameService.strikeoutSwinging(gameId, pitcherId, batterId);

        redirectAttributes.addAttribute("gameId", gameId);
        return "redirect:/games/scoreGame";
    }

    @PostMapping("/caughtFoulTip")
    public String caughtFoulTip(@RequestParam("gameId") int gameId,
                                @RequestParam("pitcherId") int pitcherId,
                                @RequestParam("batterId") int batterId,
                                RedirectAttributes redirectAttributes) {
        gameService.caughtFoulTip(gameId, pitcherId, batterId);

        redirectAttributes.addAttribute("gameId", gameId);
        return "redirect:/games/scoreGame";
    }

    @PostMapping("/buntFoul")
    public String buntFoul(@RequestParam("gameId") int gameId,
                           @RequestParam("pitcherId") int pitcherId,
                           @RequestParam("batterId") int batterId,
                           RedirectAttributes redirectAttributes) {
        gameService.buntFoul(gameId, pitcherId, batterId);

        redirectAttributes.addAttribute("gameId", gameId);
        return "redirect:/games/scoreGame";
    }

    @PostMapping("/foul")
    public String foul(@RequestParam("gameId") int gameId,
                       @RequestParam("batterId") int batterId,
                       RedirectAttributes redirectAttributes) {
        gameService.foul(gameId, batterId);

        redirectAttributes.addAttribute("gameId", gameId);
        return "redirect:/games/scoreGame";
    }

    @PostMapping("/inPlay")
    public String inPlay(@RequestParam("gameId") int gameId,
                         @RequestParam("pitcherId") int pitcherId,
                         @RequestParam("batterId") int batterId,
                         @RequestParam("firstBaseOffenceId") int firstBaseOffenceId,
                         @RequestParam("secondBaseOffenceId") int secondBaseOffenceId,
                         @RequestParam("thirdBaseOffenceId") int thirdBaseOffenceId,
                         Model theModel) {
        theModel.addAttribute("gameId", gameId);
        theModel.addAttribute("pitcherId", pitcherId);
        theModel.addAttribute("batterId", batterId);
        theModel.addAttribute("firstBaseOffenceId", firstBaseOffenceId);
        theModel.addAttribute("secondBaseOffenceId", secondBaseOffenceId);
        theModel.addAttribute("thirdBaseOffenceId", thirdBaseOffenceId);

        return "game-in-play";
    }

    @PostMapping("/walk")
    public String walk(@RequestParam("gameId") int gameId,
                       @RequestParam("pitcherId") int pitcherId,
                       @RequestParam("batterId") int batterId,
                       RedirectAttributes redirectAttributes) {
        gameService.walk(gameId, pitcherId, batterId);

        redirectAttributes.addAttribute("gameId", gameId);
        return "redirect:/games/scoreGame";
    }

    @PostMapping("/intentionalWalk")
    public String intentionalWalk(@RequestParam("gameId") int gameId,
                                  @RequestParam("pitcherId") int pitcherId,
                                  @RequestParam("batterId") int batterId,
                                  RedirectAttributes redirectAttributes) {
        gameService.intentionalWalk(gameId, pitcherId, batterId);

        redirectAttributes.addAttribute("gameId", gameId);
        return "redirect:/games/scoreGame";
    }

    @PostMapping("/caughtStealing")
    public String caughtStealing(@RequestParam("gameId") int gameId,
                                 @RequestParam("pitcherId") int pitcherId,
                                 @RequestParam("batterId") int batterId,
                                 @RequestParam("runnerId") int runnerId,
                                 @RequestParam("basemanId") int basemanId,
                                 Model theModel) {
        if(basemanId == 0) {
            return "game-out-map";
        }
        return null;
    }

    @GetMapping("/whatHappened")
    public String whatHappenedWithPlayer(@RequestParam("gameId") int gameId,
                                         @RequestParam("pitcherId") int pitcherId,
                                         @RequestParam("batterId") int batterId,
                                         @RequestParam("firstBaseOffenceId") int firstBaseOffenceId,
                                         @RequestParam("secondBaseOffenceId") int secondBaseOffenceId,
                                         @RequestParam("thirdBaseOffenceId") int thirdBaseOffenceId,
                                         Model theModel) {
        theModel.addAttribute("gameId", gameId);
        theModel.addAttribute("pitcherId", pitcherId);
        theModel.addAttribute("batterId", batterId);
        if (thirdBaseOffenceId != 0) {
            Player thirdBaseOffence = playerService.findById(thirdBaseOffenceId);
            theModel.addAttribute("baseNumber", "third base");
            theModel.addAttribute("runner", thirdBaseOffence);
            return "game-what-happened";
        }
        if (secondBaseOffenceId != 0) {
            Player secondBaseOffence = playerService.findById(secondBaseOffenceId);
            theModel.addAttribute("baseNumber", "first base");
            theModel.addAttribute("runner", secondBaseOffence);
            return "game-what-happened";
        }
        if (firstBaseOffenceId != 0) {
            Player firstBaseOffence = playerService.findById(firstBaseOffenceId);
            theModel.addAttribute("baseNumber", "first base");
            theModel.addAttribute("runner", firstBaseOffence);
            return "game-what-happened";
        }
        return null;
    }

    @PostMapping("/hitSingle")
    public String hitSingle(@RequestParam("gameId") int gameId,
                            @RequestParam("pitcherId") int pitcherId,
                            @RequestParam("batterId") int batterId,
                            @RequestParam("firstBaseOffenceId") int firstBaseOffenceId,
                            @RequestParam("secondBaseOffenceId") int secondBaseOffenceId,
                            @RequestParam("thirdBaseOffenceId") int thirdBaseOffenceId,
                            RedirectAttributes redirectAttributes) {

        if ((firstBaseOffenceId != 0) || (secondBaseOffenceId != 0) || (thirdBaseOffenceId != 0)) {
            redirectAttributes.addAttribute("gameId", gameId);
            redirectAttributes.addAttribute("pitcherId", pitcherId);
            redirectAttributes.addAttribute("batterId", batterId);
            redirectAttributes.addAttribute("firstBaseOffenceId", firstBaseOffenceId);
            redirectAttributes.addAttribute("secondBaseOffenceId", secondBaseOffenceId);
            redirectAttributes.addAttribute("thirdBaseOffenceId", thirdBaseOffenceId);
            return "redirect:/games/whatHappened";
        }
        gameService.hitSingle(gameId, pitcherId, batterId);

        redirectAttributes.addAttribute("gameId", gameId);
        return "redirect:/games/scoreGame";
    }

    @PostMapping("/hitDouble")
    public String hitDouble(@RequestParam("gameId") int gameId,
                            @RequestParam("pitcherId") int pitcherId,
                            @RequestParam("batterId") int batterId,
                            RedirectAttributes redirectAttributes) {
        gameService.hitDouble(gameId, pitcherId, batterId);

        redirectAttributes.addAttribute("gameId", gameId);
        return "redirect:/games/scoreGame";
    }

    @PostMapping("/hitTriple")
    public String hitTriple(@RequestParam("gameId") int gameId,
                            @RequestParam("pitcherId") int pitcherId,
                            @RequestParam("batterId") int batterId,
                            RedirectAttributes redirectAttributes) {
        gameService.hitTriple(gameId, pitcherId, batterId);

        redirectAttributes.addAttribute("gameId", gameId);
        return "redirect:/games/scoreGame";
    }

    @PostMapping("/homeRun")
    public String homeRun(@RequestParam("gameId") int gameId,
                          @RequestParam("pitcherId") int pitcherId,
                          @RequestParam("batterId") int batterId,
                          RedirectAttributes redirectAttributes) {
        gameService.homeRun(gameId, pitcherId, batterId);

        redirectAttributes.addAttribute("gameId", gameId);
        return "redirect:/games/scoreGame";
    }

    @PostMapping("/inParkHomeRun")
    public String inParkHomeRun(@RequestParam("gameId") int gameId,
                                @RequestParam("pitcherId") int pitcherId,
                                @RequestParam("batterId") int batterId,
                                RedirectAttributes redirectAttributes) {
        gameService.inParkHomeRun(gameId, pitcherId, batterId);

        redirectAttributes.addAttribute("gameId", gameId);
        return "redirect:/games/scoreGame";
    }

    @PostMapping("/buntSafe")
    public String buntSafe(@RequestParam("gameId") int gameId,
                           @RequestParam("pitcherId") int pitcherId,
                           @RequestParam("batterId") int batterId,
                           RedirectAttributes redirectAttributes) {
        gameService.buntSafe(gameId, pitcherId, batterId);

        redirectAttributes.addAttribute("gameId", gameId);
        return "redirect:/games/scoreGame";
    }

    @PostMapping("/error")
    public String error(@RequestParam("gameId") int gameId,
                        @RequestParam("pitcherId") int pitcherId,
                        @RequestParam("batterId") int batterId,
                        RedirectAttributes redirectAttributes) {
        gameService.error(gameId, pitcherId, batterId);

        redirectAttributes.addAttribute("gameId", gameId);
        return "redirect:/games/scoreGame";
    }

    @PostMapping("/hitByPitch")
    public String hitByPitch(@RequestParam("gameId") int gameId,
                             @RequestParam("pitcherId") int pitcherId,
                             @RequestParam("batterId") int batterId,
                             RedirectAttributes redirectAttributes) {
        gameService.hitByPitch(gameId, pitcherId, batterId);

        redirectAttributes.addAttribute("gameId", gameId);
        return "redirect:/games/scoreGame";
    }

    @PostMapping("/safe/dropped3rdStrike")
    public String dropped3rdStrikeSafe(@RequestParam("gameId") int gameId,
                                       @RequestParam("pitcherId") int pitcherId,
                                       @RequestParam("batterId") int batterId,
                                       RedirectAttributes redirectAttributes) {
        gameService.dropped3rdStrikeSafe(gameId, pitcherId, batterId);

        redirectAttributes.addAttribute("gameId", gameId);
        return "redirect:/games/scoreGame";
    }

    @PostMapping("/wildPitch3rdStrike")
    public String wildPitch3rdStrike(@RequestParam("gameId") int gameId,
                                     @RequestParam("pitcherId") int pitcherId,
                                     @RequestParam("batterId") int batterId,
                                     RedirectAttributes redirectAttributes) {
        gameService.wildPitch3rdStrike(gameId, pitcherId, batterId);

        redirectAttributes.addAttribute("gameId", gameId);
        return "redirect:/games/scoreGame";
    }

    @PostMapping("/fieldersChoice")
    public String fieldersChoice(@RequestParam("gameId") int gameId,
                                 @RequestParam("pitcherId") int pitcherId,
                                 @RequestParam("batterId") int batterId,
                                 RedirectAttributes redirectAttributes) {
        gameService.fieldersChoice(gameId, pitcherId, batterId);

        redirectAttributes.addAttribute("gameId", gameId);
        return "redirect:/games/scoreGame";
    }

    @PostMapping("/groundOut")
    public String groundOut(@RequestParam("gameId") int gameId,
                            @RequestParam("pitcherId") int pitcherId,
                            @RequestParam("batterId") int batterId,
                            RedirectAttributes redirectAttributes) {
        gameService.groundOut(gameId, pitcherId, batterId);

        redirectAttributes.addAttribute("gameId", gameId);
        return "redirect:/games/scoreGame";
    }

    @PostMapping("/lineDrive")
    public String lineDrive(@RequestParam("gameId") int gameId,
                            @RequestParam("pitcherId") int pitcherId,
                            @RequestParam("batterId") int batterId,
                            RedirectAttributes redirectAttributes) {
        gameService.lineDrive(gameId, pitcherId, batterId);

        redirectAttributes.addAttribute("gameId", gameId);
        return "redirect:/games/scoreGame";
    }

    @PostMapping("/flyOut")
    public String flyOut(@RequestParam("gameId") int gameId,
                         @RequestParam("pitcherId") int pitcherId,
                         @RequestParam("batterId") int batterId,
                         RedirectAttributes redirectAttributes) {
        gameService.flyOut(gameId, pitcherId, batterId);

        redirectAttributes.addAttribute("gameId", gameId);
        return "redirect:/games/scoreGame";
    }

    @PostMapping("/buntOut")
    public String buntOut(@RequestParam("gameId") int gameId,
                          @RequestParam("pitcherId") int pitcherId,
                          @RequestParam("batterId") int batterId,
                          RedirectAttributes redirectAttributes) {
        gameService.buntOut(gameId, pitcherId, batterId);

        redirectAttributes.addAttribute("gameId", gameId);
        return "redirect:/games/scoreGame";
    }

    @PostMapping("/sacrificeFly")
    public String sacrificeFly(@RequestParam("gameId") int gameId,
                               @RequestParam("pitcherId") int pitcherId,
                               @RequestParam("batterId") int batterId,
                               RedirectAttributes redirectAttributes) {
        gameService.sacrificeFly(gameId, pitcherId, batterId);

        redirectAttributes.addAttribute("gameId", gameId);
        return "redirect:/games/scoreGame";
    }

    @PostMapping("/sacrificeBunt")
    public String sacrificeBunt(@RequestParam("gameId") int gameId,
                                @RequestParam("pitcherId") int pitcherId,
                                @RequestParam("batterId") int batterId,
                                RedirectAttributes redirectAttributes) {
        gameService.sacrificeBunt(gameId, pitcherId, batterId);

        redirectAttributes.addAttribute("gameId", gameId);
        return "redirect:/games/scoreGame";
    }

    @PostMapping("/infieldFly")
    public String infieldFly(@RequestParam("gameId") int gameId,
                             @RequestParam("pitcherId") int pitcherId,
                             @RequestParam("batterId") int batterId,
                             RedirectAttributes redirectAttributes) {
        gameService.infieldFly(gameId, pitcherId, batterId);

        redirectAttributes.addAttribute("gameId", gameId);
        return "redirect:/games/scoreGame";
    }

    @PostMapping("/hitByBall")
    public String hitByBall(@RequestParam("gameId") int gameId,
                            @RequestParam("pitcherId") int pitcherId,
                            @RequestParam("batterId") int batterId,
                            RedirectAttributes redirectAttributes) {
        gameService.hitByBall(gameId, pitcherId, batterId);

        redirectAttributes.addAttribute("gameId", gameId);
        return "redirect:/games/scoreGame";
    }

    @PostMapping("/out/dropped3rdStrike")
    public String dropped3rdStrikeOut(@RequestParam("gameId") int gameId,
                                      @RequestParam("pitcherId") int pitcherId,
                                      @RequestParam("batterId") int batterId,
                                      RedirectAttributes redirectAttributes) {
        gameService.dropped3rdStrikeOut(gameId, pitcherId, batterId);

        redirectAttributes.addAttribute("gameId", gameId);
        return "redirect:/games/scoreGame";
    }

    @PostMapping("/runnerInterference")
    public String runnerInterference(@RequestParam("gameId") int gameId,
                                     @RequestParam("pitcherId") int pitcherId,
                                     @RequestParam("batterId") int batterId,
                                     RedirectAttributes redirectAttributes) {
        gameService.runnerInterference(gameId, pitcherId, batterId);

        redirectAttributes.addAttribute("gameId", gameId);
        return "redirect:/games/scoreGame";
    }

    @PostMapping("/offensiveInterference")
    public String offensiveInterference(@RequestParam("gameId") int gameId,
                                        @RequestParam("pitcherId") int pitcherId,
                                        @RequestParam("batterId") int batterId,
                                        RedirectAttributes redirectAttributes) {
        gameService.offensiveInterference(gameId, pitcherId, batterId);

        redirectAttributes.addAttribute("gameId", gameId);
        return "redirect:/games/scoreGame";
    }

    private Player getCurrentBatter(List<Player> players) {
        for (Player player : players) {
            if (player.getOffencePosition() != null && player.getOffencePosition().equalsIgnoreCase(Constants.batter)) {
                return player;
            }
        }
        return null;
    }

    private Player getCurrentFirstBaseOffence(List<Player> players) {
        for (Player player : players) {
            if (player.getOffencePosition() != null && player.getOffencePosition().equalsIgnoreCase(Constants.runnerFirstBase)) {
                return player;
            }
        }
        return null;
    }

    private Player getCurrentSecondBaseOffence(List<Player> players) {
        for (Player player : players) {
            if (player.getOffencePosition() != null && player.getOffencePosition().equalsIgnoreCase(Constants.runnerSecondBase)) {
                return player;
            }
        }
        return null;
    }

    private Player getCurrentThirdBaseOffence(List<Player> players) {
        for (Player player : players) {
            if (player.getOffencePosition() != null && player.getOffencePosition().equalsIgnoreCase(Constants.runnerThirdBase)) {
                return player;
            }
        }
        return null;
    }

    private Player getCurrentPitcher(List<Player> players) {
        for (Player player : players) {
            if (player.getDefencePosition() != null && player.getDefencePosition().equalsIgnoreCase(Constants.pitcher)) {
                return player;
            }
        }
        return null;
    }

    private Player getCurrentCatcher(List<Player> players) {
        for (Player player : players) {
            if (player.getDefencePosition() != null && player.getDefencePosition().equalsIgnoreCase(Constants.catcher)) {
                return player;
            }
        }
        return null;
    }

    private Player getCurrentFirstBaseDefence(List<Player> players) {
        for (Player player : players) {
            if (player.getDefencePosition() != null && player.getDefencePosition().equalsIgnoreCase(Constants.defenderFirstBase)) {
                return player;
            }
        }
        return null;
    }

    private Player getCurrentSecondBaseDefence(List<Player> players) {
        for (Player player : players) {
            if (player.getDefencePosition() != null && player.getDefencePosition().equalsIgnoreCase(Constants.defenderSecondBase)) {
                return player;
            }
        }
        return null;
    }

    private Player getCurrentShortStop(List<Player> players) {
        for (Player player : players) {
            if (player.getDefencePosition() != null && player.getDefencePosition().equalsIgnoreCase(Constants.shortStop)) {
                return player;
            }
        }
        return null;
    }

    private Player getThirdBaseDefence(List<Player> players) {
        for (Player player : players) {
            if (player.getDefencePosition() != null && player.getDefencePosition().equalsIgnoreCase(Constants.defenderThirdBase)) {
                return player;
            }
        }
        return null;
    }

    private Player getRightField(List<Player> players) {
        for (Player player : players) {
            if (player.getDefencePosition() != null && player.getDefencePosition().equalsIgnoreCase(Constants.rightField)) {
                return player;
            }
        }
        return null;
    }

    private Player getCenterField(List<Player> players) {
        for (Player player : players) {
            if (player.getDefencePosition() != null && player.getDefencePosition().equalsIgnoreCase(Constants.centerField)) {
                return player;
            }
        }
        return null;
    }

    private Player getLeftField(List<Player> players) {
        for (Player player : players) {
            if (player.getDefencePosition() != null && player.getDefencePosition().equalsIgnoreCase(Constants.leftField)) {
                return player;
            }
        }
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
        if ((game.getInnings() != null) && (game.getInnings().isEmpty())) {
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
