package com.junak.scorekeeper.client.service.impl;

import com.junak.scorekeeper.client.model.Game;
import com.junak.scorekeeper.client.model.GamePitchingDetails;
import com.junak.scorekeeper.client.model.Player;
import com.junak.scorekeeper.client.service.interfaces.GamePitchingDetailsService;
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
public class GamePitchingDetailsServiceRestClientImpl implements GamePitchingDetailsService {
    private RestTemplate restTemplate;
    private String scorekeeperRestUrl;
    private String gamePitchingDetailsRestUrl;

    private Logger logger = Logger.getLogger(getClass().getName());

    @Autowired
    public GamePitchingDetailsServiceRestClientImpl(RestTemplateBuilder builder,
                                            @Value("${scorekeeper.rest.url}") String theUrl) {
        restTemplate = builder.build();
        scorekeeperRestUrl = theUrl;
        gamePitchingDetailsRestUrl = scorekeeperRestUrl + "/gamePitchingDetails";
        logger.info("Loaded property: scorekeeper.rest.url=" + gamePitchingDetailsRestUrl);
    }

    @Override
    public List<GamePitchingDetails> findAll() {
        logger.info("in findAllGamePitchingDetails(): Calling REST API " + gamePitchingDetailsRestUrl);

        // make REST call
        ResponseEntity<List<GamePitchingDetails>> responseEntity =
                restTemplate.exchange(gamePitchingDetailsRestUrl, HttpMethod.GET, null,
                        new ParameterizedTypeReference<List<GamePitchingDetails>>() {});

        // get the list of gamePitchingDetailss from response
        List<GamePitchingDetails> gamePitchingDetailss = responseEntity.getBody();

        logger.info("in findAllGamePitchingDetailss(): gamePitchingDetailss" + gamePitchingDetailss);

        return gamePitchingDetailss;
    }

    @Override
    public GamePitchingDetails findById(int id) {
        logger.info("in findById(): Calling REST API " + gamePitchingDetailsRestUrl + "/" + id);

        // make REST call
        GamePitchingDetails theGamePitchingDetails =
                restTemplate.getForObject(gamePitchingDetailsRestUrl + "/" + id, GamePitchingDetails.class);

        logger.info("in findById(): theGamePitchingDetails=" + theGamePitchingDetails);

        return theGamePitchingDetails;
    }

    @Override
    public void save(GamePitchingDetails gamePitchingDetails) {
        logger.info("in save(): Calling REST API " + gamePitchingDetailsRestUrl);

        int gamePitchingDetailsId = gamePitchingDetails.getId();

        // make REST call
        if (gamePitchingDetailsId == 0) {
            // add gamePitchingDetails
            restTemplate.postForEntity(gamePitchingDetailsRestUrl, gamePitchingDetails, String.class);

        } else {
            // update gamePitchingDetails
            restTemplate.put(gamePitchingDetailsRestUrl, gamePitchingDetails);
        }

        logger.info("in save(): success");
    }

    @Override
    public void deleteById(int id) {
        logger.info("in deleteGamePitchingDetails(): Calling REST API " + gamePitchingDetailsRestUrl);

        // make REST call
        restTemplate.delete(gamePitchingDetailsRestUrl + "/" + id);

        logger.info("in deleteGamePitchingDetails(): deleted gamePitchingDetails id=" + id);
    }

    @Override
    public GamePitchingDetails getGamePitchingDetails(Player player, Game game) {
        String gamePitchingDetailsPlayerGameRestUrl = gamePitchingDetailsRestUrl + "/game/" + game.getId() + "/player/"
                + player.getId();
        logger.info("in getGamePitchingDetails(): Calling REST API " + gamePitchingDetailsPlayerGameRestUrl);

        // make REST call
        GamePitchingDetails theGamePitchingDetails =
                restTemplate.getForObject(gamePitchingDetailsPlayerGameRestUrl, GamePitchingDetails.class);

        logger.info("in getGamePitchingDetails(): theGamePitchingDetails=" + theGamePitchingDetails);

        return theGamePitchingDetails;
    }
}
