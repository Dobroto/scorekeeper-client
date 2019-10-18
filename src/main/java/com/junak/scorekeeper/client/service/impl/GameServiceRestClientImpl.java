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
                        new ParameterizedTypeReference<List<Game>>() {});

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
    public void deleteById(int id) {
        logger.info("in deleteGame(): Calling REST API " + gameRestUrl);

        // make REST call
        restTemplate.delete(gameRestUrl + "/" + id);

        logger.info("in deleteGame(): deleted game id=" + id);
    }
}
