package com.junak.scorekeeper.client.service.impl;

import com.junak.scorekeeper.client.model.Play;
import com.junak.scorekeeper.client.service.interfaces.PlayService;
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
public class PlayServiceRestClientImpl implements PlayService {

    private RestTemplate restTemplate;
    private String scorekeeperRestUrl;
    private String playRestUrl;

    private Logger logger = Logger.getLogger(getClass().getName());

    @Autowired
    public PlayServiceRestClientImpl(RestTemplateBuilder builder,
                                       @Value("${scorekeeper.rest.url}") String theUrl) {
        restTemplate = builder.build();
        scorekeeperRestUrl = theUrl;
        playRestUrl = scorekeeperRestUrl + "/plays";
        logger.info("Loaded property: scorekeeper.rest.url=" + playRestUrl);
    }

    @Override
    public List<Play> getAllPlaysInGame(int gameId) {
        String gamePlaysRestUrl = playRestUrl + "/" + gameId;
        logger.info("in getAllPlaysInGame(): Calling REST API " + gamePlaysRestUrl);

        // make REST call
        ResponseEntity<List<Play>> responseEntity =
                restTemplate.exchange(gamePlaysRestUrl, HttpMethod.GET, null,
                        new ParameterizedTypeReference<List<Play>>() {});

        // get the list of players from response
        List<Play> plays = responseEntity.getBody();

        logger.info("in getAllPlaysInGame(): plays" + plays);

        return plays;
    }
}
