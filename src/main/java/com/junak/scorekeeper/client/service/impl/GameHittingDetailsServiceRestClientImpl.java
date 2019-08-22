package com.junak.scorekeeper.client.service.impl;

import com.junak.scorekeeper.client.model.GameHittingDetails;
import com.junak.scorekeeper.client.service.interfaces.GameHittingDetailsService;
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
public class GameHittingDetailsServiceRestClientImpl implements GameHittingDetailsService {
    private RestTemplate restTemplate;
    private String scorekeeperRestUrl;
    private String gameHittingDetailsRestUrl;

    private Logger logger = Logger.getLogger(getClass().getName());

    @Autowired
    public GameHittingDetailsServiceRestClientImpl(RestTemplateBuilder builder,
                                            @Value("${scorekeeper.rest.url}") String theUrl) {
        restTemplate = builder.build();
        scorekeeperRestUrl = theUrl;
        gameHittingDetailsRestUrl = scorekeeperRestUrl + "/gameHittingDetails";
        logger.info("Loaded property: scorekeeper.rest.url=" + gameHittingDetailsRestUrl);
    }

    @Override
    public List<GameHittingDetails> findAll() {
        logger.info("in findAllGameHittingDetails(): Calling REST API " + gameHittingDetailsRestUrl);

        // make REST call
        ResponseEntity<List<GameHittingDetails>> responseEntity =
                restTemplate.exchange(gameHittingDetailsRestUrl, HttpMethod.GET, null,
                        new ParameterizedTypeReference<List<GameHittingDetails>>() {});

        // get the list of gameHittingDetailss from response
        List<GameHittingDetails> gameHittingDetailss = responseEntity.getBody();

        logger.info("in findAllGameHittingDetails(): gameHittingDetails" + gameHittingDetailss);

        return gameHittingDetailss;
    }

    @Override
    public GameHittingDetails findById(int id) {
        logger.info("in findById(): Calling REST API " + gameHittingDetailsRestUrl + "/" + id);

        // make REST call
        GameHittingDetails theGameHittingDetails =
                restTemplate.getForObject(gameHittingDetailsRestUrl + "/" + id, GameHittingDetails.class);

        logger.info("in findById(): theGameHittingDetails=" + theGameHittingDetails);

        return theGameHittingDetails;
    }

    @Override
    public void save(GameHittingDetails gameHittingDetails) {
        logger.info("in save(): Calling REST API " + gameHittingDetailsRestUrl);

        int gameHittingDetailsId = gameHittingDetails.getId();

        // make REST call
        if (gameHittingDetailsId == 0) {
            // add gameHittingDetails
            restTemplate.postForEntity(gameHittingDetailsRestUrl, gameHittingDetails, String.class);

        } else {
            // update gameHittingDetails
            restTemplate.put(gameHittingDetailsRestUrl, gameHittingDetails);
        }

        logger.info("in save(): success");
    }

    @Override
    public void deleteById(int id) {
        logger.info("in deleteGameHittingDetails(): Calling REST API " + gameHittingDetailsRestUrl);

        // make REST call
        restTemplate.delete(gameHittingDetailsRestUrl + "/" + id);

        logger.info("in deleteGameHittingDetails(): deleted gameHittingDetails id=" + id);
    }
}
