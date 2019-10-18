package com.junak.scorekeeper.client.service.impl;

import com.junak.scorekeeper.client.model.Game;
import com.junak.scorekeeper.client.model.GameFieldingDetails;
import com.junak.scorekeeper.client.model.Player;
import com.junak.scorekeeper.client.service.interfaces.GameFieldingDetailsService;
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
public class GameFieldingDetailsServiceRestClientImpl implements GameFieldingDetailsService {
    private RestTemplate restTemplate;
    private String scorekeeperRestUrl;
    private String gameFieldingDetailsRestUrl;

    private Logger logger = Logger.getLogger(getClass().getName());

    @Autowired
    public GameFieldingDetailsServiceRestClientImpl(RestTemplateBuilder builder,
                                            @Value("${scorekeeper.rest.url}") String theUrl) {
        restTemplate = builder.build();
        scorekeeperRestUrl = theUrl;
        gameFieldingDetailsRestUrl = scorekeeperRestUrl + "/gameFieldingDetails";
        logger.info("Loaded property: scorekeeper.rest.url=" + gameFieldingDetailsRestUrl);
    }

    @Override
    public List<GameFieldingDetails> findAll() {
        logger.info("in findAllGameFieldingDetails(): Calling REST API " + gameFieldingDetailsRestUrl);

        // make REST call
        ResponseEntity<List<GameFieldingDetails>> responseEntity =
                restTemplate.exchange(gameFieldingDetailsRestUrl, HttpMethod.GET, null,
                        new ParameterizedTypeReference<List<GameFieldingDetails>>() {});

        // get the list of gameFieldingDetails from response
        List<GameFieldingDetails> gameFieldingDetails = responseEntity.getBody();

        logger.info("in findAllGameFieldingDetails(): gameFieldingDetails" + gameFieldingDetails);

        return gameFieldingDetails;
    }

    @Override
    public GameFieldingDetails findById(int id) {
        logger.info("in findById(): Calling REST API " + gameFieldingDetailsRestUrl + "/" + id);

        // make REST call
        GameFieldingDetails theGameFieldingDetails =
                restTemplate.getForObject(gameFieldingDetailsRestUrl + "/" + id, GameFieldingDetails.class);

        logger.info("in findById(): theGameFieldingDetails=" + theGameFieldingDetails);

        return theGameFieldingDetails;
    }

    @Override
    public void save(GameFieldingDetails gameFieldingDetails) {
        logger.info("in save(): Calling REST API " + gameFieldingDetailsRestUrl);

        int gameFieldingDetailsId = gameFieldingDetails.getId();

        // make REST call
        if (gameFieldingDetailsId == 0) {
            // add gameFieldingDetails
            restTemplate.postForEntity(gameFieldingDetailsRestUrl, gameFieldingDetails, String.class);

        } else {
            // update gameFieldingDetails
            restTemplate.put(gameFieldingDetailsRestUrl, gameFieldingDetails);
        }

        logger.info("in save(): success");
    }

    @Override
    public void deleteById(int id) {
        logger.info("in deleteFinalResult(): Calling REST API " + gameFieldingDetailsRestUrl);

        // make REST call
        restTemplate.delete(gameFieldingDetailsRestUrl + "/" + id);

        logger.info("in deleteFinalResult(): deleted gameFieldingDetails id=" + id);
    }

    @Override
    public GameFieldingDetails getGameFieldingDetails(Player player, Game game) {
        String gameFieldingDetailsPlayerGameRestUrl = gameFieldingDetailsRestUrl + "/game/" + game.getId() + "/player/"
                + player.getId();
        logger.info("in getGameFieldingDetails(): Calling REST API " + gameFieldingDetailsPlayerGameRestUrl);

        // make REST call
        GameFieldingDetails theGameFieldingDetails =
                restTemplate.getForObject(gameFieldingDetailsPlayerGameRestUrl, GameFieldingDetails.class);

        logger.info("in getGameFieldingDetails(): theGameFieldingDetails=" + theGameFieldingDetails);

        return theGameFieldingDetails;
    }
}
