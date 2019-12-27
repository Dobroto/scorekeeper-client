package com.junak.scorekeeper.client.service.impl;

import com.junak.scorekeeper.client.model.Game;
import com.junak.scorekeeper.client.service.interfaces.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.logging.Logger;

@Service
public class GameServiceRestClientImpl implements GameService {
    private RestTemplate restTemplate;
    private String scorekeeperRestUrl;
    private String gameRestUrl;

    private Logger logger = Logger.getLogger(getClass().getName());

    @Autowired
    public GameServiceRestClientImpl(RestTemplateBuilder builder,
                                     @Value("${scorekeeper.rest.url}") String theUrl) {
        restTemplate = builder.build();
        scorekeeperRestUrl = theUrl;
        gameRestUrl = scorekeeperRestUrl + "/games";
        logger.info("Loaded property: scorekeeper.rest.url=" + gameRestUrl);
    }

    @Override
    public List<Game> findAll() {
        logger.info("in findAllGames(): Calling REST API " + gameRestUrl);

        // make REST call
        ResponseEntity<List<Game>> responseEntity =
                restTemplate.exchange(gameRestUrl, HttpMethod.GET, null,
                        new ParameterizedTypeReference<List<Game>>() {
                        });

        // get the list of games from response
        List<Game> games = responseEntity.getBody();

        logger.info("in findAllGames(): games" + games);

        return games;
    }

    @Override
    public Game findById(int id) {
        logger.info("in findById(): Calling REST API " + gameRestUrl + "/" + id);

        // make REST call
        Game theGame =
                restTemplate.getForObject(gameRestUrl + "/" + id, Game.class);

        logger.info("in findById(): theGame=" + theGame);

        return theGame;
    }

    @Override
    public void save(Game game) {
        logger.info("in save(): Calling REST API " + gameRestUrl);

        int gameId = game.getId();

        // make REST call
        if (gameId == 0) {
            // add game
            restTemplate.postForEntity(gameRestUrl, game, String.class);

        } else {
            // update game
            restTemplate.put(gameRestUrl, game);
        }

        logger.info("in save(): success");
    }

    @Override
    public void deleteGame(int id) {
        String deleteGameUrl = gameRestUrl + "/" + id;
        logger.info("in deleteGame(): Calling REST API " + deleteGameUrl);

        // make REST call
        restTemplate.delete(deleteGameUrl);

        logger.info("in deleteGame(): deleted game id=" + id);
    }

    @Override
    public void createNewGame(int homeTeamId, int visitorTeamId) {
        String createNewGameUrl = gameRestUrl + "/" + homeTeamId + "/" + visitorTeamId + "/createNewGame";
        logger.info("in createNewGame(): Calling REST API " + createNewGameUrl);

        restTemplate.postForEntity(createNewGameUrl, null, null);
    }

    @Override
    public void startGame(int gameId) {
        String startGameUrl = gameRestUrl + "/" + gameId + "/play";
        logger.info("in startGame(): Calling REST API " + startGameUrl);

        restTemplate.put(startGameUrl, null);
    }

    @Override
    public void ball(int gameId, int pitcherId, int batterId) {
        String ballUrl = gameRestUrl + "/" + gameId + "/" + pitcherId + "/" + batterId + "/ball";
        logger.info("in ball(): Calling REST API " + ballUrl);

        restTemplate.put(ballUrl, null);
    }

    @Override
    public void strike(int pitcherId, int batterId) {
        String strikeUrl = gameRestUrl + "/" + pitcherId + "/" + batterId + "/strike";
        logger.info("in strike(): Calling REST API " + strikeUrl);

        restTemplate.put(strikeUrl, null);
    }

    @Override
    public void strikeoutLooking(int gameId, int pitcherId, int batterId) {
        String strikeoutLookingUrl = gameRestUrl + "/" + gameId + "/" + pitcherId + "/" + batterId + "/strikeoutLooking";
        logger.info("in strikeoutLooking(): Calling REST API " + strikeoutLookingUrl);

        restTemplate.put(strikeoutLookingUrl, null);
    }

    @Override
    public void strikeoutSwinging(int gameId, int pitcherId, int batterId) {
        String strikeoutSwingingUrl = gameRestUrl + "/" + gameId + "/" + pitcherId + "/" + batterId + "/strikeoutSwinging";
        logger.info("in strikeoutSwinging(): Calling REST API " + strikeoutSwingingUrl);

        restTemplate.put(strikeoutSwingingUrl, null);
    }

    @Override
    public void caughtFoulTip(int gameId, int pitcherId, int batterId) {
        String caughtFoulTipUrl = gameRestUrl + "/" + gameId + "/" + pitcherId + "/" + batterId + "/caughtFoulTip";
        logger.info("in caughtFoulTip(): Calling REST API " + caughtFoulTipUrl);

        restTemplate.put(caughtFoulTipUrl, null);
    }

    @Override
    public void buntFoul(int gameId, int pitcherId, int batterId) {
        String buntFoulUrl = gameRestUrl + "/" + gameId + "/" + pitcherId + "/" + batterId + "/buntFoul";
        logger.info("in buntFoul(): Calling REST API " + buntFoulUrl);

        restTemplate.put(buntFoulUrl, null);
    }

    @Override
    public void foul(int gameId, int batterId) {
        String foulUrl = gameRestUrl + "/" + gameId + "/" + batterId + "/foul";
        logger.info("in foul(): Calling REST API " + foulUrl);

        restTemplate.put(foulUrl, null);
    }

    @Override
    public void walk(int gameId, int pitcherId, int batterId) {
        String walkUrl = gameRestUrl + "/" + gameId + "/" + pitcherId + "/" + batterId + "/walk";
        logger.info("in walk(): Calling REST API " + walkUrl);

        restTemplate.put(walkUrl, null);
    }

    @Override
    public void intentionalWalk(int gameId, int pitcherId, int batterId) {
        String intentionalWalkUrl = gameRestUrl + "/" + gameId + "/" + pitcherId + "/" + batterId + "/intentionalWalk";
        logger.info("in intentionalWalk(): Calling REST API " + intentionalWalkUrl);

        restTemplate.put(intentionalWalkUrl, null);
    }

    @Override
    public void dropped3rdStrikeOut(int gameId, int pitcherId, int batterId) {
        String dropped3rdStrikeOutUrl = gameRestUrl + "/" + gameId + "/" + pitcherId + "/" + batterId + "/out/dropped3rdStrike";
        logger.info("in dropped3rdStrikeOut(): Calling REST API " + dropped3rdStrikeOutUrl);

        restTemplate.put(dropped3rdStrikeOutUrl, null);
    }

    @Override
    public void dropped3rdStrikeSafe(int gameId, int pitcherId, int batterId) {
        String dropped3rdStrikeSafeUrl = gameRestUrl + "/" + gameId + "/" + pitcherId + "/" + batterId + "/safe/dropped3rdStrike";
        logger.info("in dropped3rdStrikeSafe(): Calling REST API " + dropped3rdStrikeSafeUrl);

        restTemplate.put(dropped3rdStrikeSafeUrl, null);
    }

    @Override
    public void wildPitch3rdStrike(int gameId, int pitcherId, int batterId) {
        String wildPitch3rdStrikeUrl = gameRestUrl + "/" + gameId + "/" + pitcherId + "/" + batterId + "/wildPitch3rdStrike";
        logger.info("in wildPitch3rdStrike(): Calling REST API " + wildPitch3rdStrikeUrl);

        restTemplate.put(wildPitch3rdStrikeUrl, null);
    }

    @Override
    public void caughtStealing(int gameId, int pitcherId, int runnerId, int catcherId, int basemanId) {
        String caughtStealingUrl = gameRestUrl + "/" + gameId + "/" + pitcherId + "/" + runnerId + "/" + catcherId + "/" + basemanId + "/caughtStealing";
        logger.info("in caughtStealing(): Calling REST API " + caughtStealingUrl);

        restTemplate.put(caughtStealingUrl, null);
    }

    @Override
    public void stolenBase(int gameId, int pitcherId, int runnerId, String baseStolen) {
        String stolenBaseUrl = gameRestUrl + "/" + gameId + "/" + pitcherId + "/" + runnerId + "/" + baseStolen + "/stolenBase";
        logger.info("in stolenBase(): Calling REST API " + stolenBaseUrl);

        restTemplate.put(stolenBaseUrl, null);
    }

    @Override
    public void hitSingle(int gameId, int pitcherId, int batterId) {
        String hitSingleUrl = gameRestUrl + "/" + gameId + "/" + pitcherId + "/" + batterId + "/hitSingle";
        logger.info("in hitSingle(): Calling REST API " + hitSingleUrl);

        restTemplate.put(hitSingleUrl, null);
    }

    @Override
    public void hitDouble(int gameId, int pitcherId, int batterId) {
        String hitDoubleUrl = gameRestUrl + "/" + gameId + "/" + pitcherId + "/" + batterId + "/hitDouble";
        logger.info("in hitDouble(): Calling REST API " + hitDoubleUrl);

        restTemplate.put(hitDoubleUrl, null);
    }

    @Override
    public void hitTriple(int gameId, int pitcherId, int batterId) {
        String hitTripleUrl = gameRestUrl + "/" + gameId + "/" + pitcherId + "/" + batterId + "/hitTriple";
        logger.info("in hitTriple(): Calling REST API " + hitTripleUrl);

        restTemplate.put(hitTripleUrl, null);
    }

    @Override
    public void homeRun(int gameId, int pitcherId, int batterId) {
        String homeRunUrl = gameRestUrl + "/" + gameId + "/" + pitcherId + "/" + batterId + "/homeRun";
        logger.info("in homeRun(): Calling REST API " + homeRunUrl);

        restTemplate.put(homeRunUrl, null);
    }

    @Override
    public void inParkHomeRun(int gameId, int pitcherId, int batterId) {
        String inParkHomeRunUrl = gameRestUrl + "/" + gameId + "/" + pitcherId + "/" + batterId + "/inParkHomeRun";
        logger.info("in inParkHomeRun(): Calling REST API " + inParkHomeRunUrl);

        restTemplate.put(inParkHomeRunUrl, null);
    }

    @Override
    public void buntSafe(int gameId, int pitcherId, int batterId) {
        String buntSafeUrl = gameRestUrl + "/" + gameId + "/" + pitcherId + "/" + batterId + "/safe/bunt";
        logger.info("in buntSafe(): Calling REST API " + buntSafeUrl);

        restTemplate.put(buntSafeUrl, null);
    }

    @Override
    public void buntOut(int gameId, int pitcherId, int batterId) {
        String buntOutUrl = gameRestUrl + "/" + gameId + "/" + pitcherId + "/" + batterId + "/out/bunt";
        logger.info("in buntOut(): Calling REST API " + buntOutUrl);

        restTemplate.put(buntOutUrl, null);
    }

    @Override
    public void error(int gameId, int pitcherId, int batterId) {
        String errorUrl = gameRestUrl + "/" + gameId + "/" + pitcherId + "/" + batterId + "/error";
        logger.info("in error(): Calling REST API " + errorUrl);

        restTemplate.put(errorUrl, null);
    }

    @Override
    public void hitByPitch(int gameId, int pitcherId, int batterId) {
        String hitByPitchUrl = gameRestUrl + "/" + gameId + "/" + pitcherId + "/" + batterId + "/hitByPitch";
        logger.info("in hitByPitch(): Calling REST API " + hitByPitchUrl);

        restTemplate.put(hitByPitchUrl, null);
    }

    @Override
    public void fieldersChoice(int gameId, int pitcherId, int batterId) {
        String fieldersChoiceUrl = gameRestUrl + "/" + gameId + "/" + pitcherId + "/" + batterId + "/fieldersChoice";
        logger.info("in fieldersChoice(): Calling REST API " + fieldersChoiceUrl);

        restTemplate.put(fieldersChoiceUrl, null);
    }

    @Override
    public void groundOut(int gameId, int pitcherId, int batterId) {
        String groundOutUrl = gameRestUrl + "/" + gameId + "/" + pitcherId + "/" + batterId + "/groundOut";
        logger.info("in groundOut(): Calling REST API " + groundOutUrl);

        restTemplate.put(groundOutUrl, null);
    }

    @Override
    public void lineDrive(int gameId, int pitcherId, int batterId) {
        String groundOutUrl = gameRestUrl + "/" + gameId + "/" + pitcherId + "/" + batterId + "/groundOut";
        logger.info("in groundOut(): Calling REST API " + groundOutUrl);

        restTemplate.put(groundOutUrl, null);
    }

    @Override
    public void flyOut(int gameId, int pitcherId, int batterId) {
        String flyOutUrl = gameRestUrl + "/" + gameId + "/" + pitcherId + "/" + batterId + "/flyOut";
        logger.info("in flyOut(): Calling REST API " + flyOutUrl);

        restTemplate.put(flyOutUrl, null);
    }

    @Override
    public void sacrificeFly(int gameId, int pitcherId, int batterId) {
        String sacrificeFlyUrl = gameRestUrl + "/" + gameId + "/" + pitcherId + "/" + batterId + "/sacrificeFly";
        logger.info("in sacrificeFly(): Calling REST API " + sacrificeFlyUrl);

        restTemplate.put(sacrificeFlyUrl, null);
    }

    @Override
    public void sacrificeBunt(int gameId, int pitcherId, int batterId) {
        String sacrificeBuntUrl = gameRestUrl + "/" + gameId + "/" + pitcherId + "/" + batterId + "/sacrificeBunt";
        logger.info("in sacrificeBunt(): Calling REST API " + sacrificeBuntUrl);

        restTemplate.put(sacrificeBuntUrl, null);
    }

    @Override
    public void infieldFly(int gameId, int pitcherId, int batterId) {
        String infieldFlyUrl = gameRestUrl + "/" + gameId + "/" + pitcherId + "/" + batterId + "/infieldFly";
        logger.info("in infieldFly(): Calling REST API " + infieldFlyUrl);

        restTemplate.put(infieldFlyUrl, null);
    }

    @Override
    public void hitByBall(int gameId, int pitcherId, int batterId) {
        String hitByBallUrl = gameRestUrl + "/" + gameId + "/" + pitcherId + "/" + batterId + "/hitByBall";
        logger.info("in hitByBall(): Calling REST API " + hitByBallUrl);

        restTemplate.put(hitByBallUrl, null);
    }

    @Override
    public void runnerInterference(int gameId, int pitcherId, int batterId) {
        String runnerInterferenceUrl = gameRestUrl + "/" + gameId + "/" + pitcherId + "/" + batterId + "/runnerInterference";
        logger.info("in runnerInterference(): Calling REST API " + runnerInterferenceUrl);

        restTemplate.put(runnerInterferenceUrl, null);
    }

    @Override
    public void offensiveInterference(int gameId, int pitcherId, int batterId) {
        String offensiveInterferenceUrl = gameRestUrl + "/" + gameId + "/" + pitcherId + "/" + batterId + "/offensiveInterference";
        logger.info("in offensiveInterference(): Calling REST API " + offensiveInterferenceUrl);

        restTemplate.put(offensiveInterferenceUrl, null);
    }


}
