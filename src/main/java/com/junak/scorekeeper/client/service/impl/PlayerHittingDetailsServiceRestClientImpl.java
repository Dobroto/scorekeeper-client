package com.junak.scorekeeper.client.service.impl;

import com.junak.scorekeeper.client.model.PlayerHittingDetails;
import com.junak.scorekeeper.client.service.interfaces.PlayerHittingDetailsService;
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
public class PlayerHittingDetailsServiceRestClientImpl implements PlayerHittingDetailsService {
    private RestTemplate restTemplate;
    private String scorekeeperRestUrl;
    private String playerHittingDetailsRestUrl;

    private Logger logger = Logger.getLogger(getClass().getName());

    @Autowired
    public PlayerHittingDetailsServiceRestClientImpl(RestTemplateBuilder builder,
                                            @Value("${scorekeeper.rest.url}") String theUrl) {
        restTemplate = builder.build();
        scorekeeperRestUrl = theUrl;
        playerHittingDetailsRestUrl = scorekeeperRestUrl + "/playerHittingDetails";
        logger.info("Loaded property: scorekeeper.rest.url=" + playerHittingDetailsRestUrl);
    }

    @Override
    public List<PlayerHittingDetails> findAll() {
        logger.info("in findAllPlayerHittingDetails(): Calling REST API " + playerHittingDetailsRestUrl);

        // make REST call
        ResponseEntity<List<PlayerHittingDetails>> responseEntity =
                restTemplate.exchange(playerHittingDetailsRestUrl, HttpMethod.GET, null,
                        new ParameterizedTypeReference<List<PlayerHittingDetails>>() {});

        // get the list of playerHittingDetails from response
        List<PlayerHittingDetails> playerHittingDetails = responseEntity.getBody();

        logger.info("in findAllPlayerHittingDetails(): playerHittingDetails" + playerHittingDetails);

        return playerHittingDetails;
    }

    @Override
    public PlayerHittingDetails findById(int id) {
        logger.info("in findById(): Calling REST API " + playerHittingDetailsRestUrl + "/" + id);

        // make REST call
        PlayerHittingDetails thePlayerHittingDetails =
                restTemplate.getForObject(playerHittingDetailsRestUrl + "/" + id, PlayerHittingDetails.class);

        logger.info("in findById(): thePlayerHittingDetails=" + thePlayerHittingDetails);

        return thePlayerHittingDetails;
    }

    @Override
    public void save(PlayerHittingDetails playerHittingDetails) {
        logger.info("in save(): Calling REST API " + playerHittingDetailsRestUrl);

        int playerHittingDetailsId = playerHittingDetails.getId();

        // make REST call
        if (playerHittingDetailsId == 0) {
            // add playerHittingDetails
            restTemplate.postForEntity(playerHittingDetailsRestUrl, playerHittingDetails, String.class);

        } else {
            // update playerHittingDetails
            restTemplate.put(playerHittingDetailsRestUrl, playerHittingDetails);
        }

        logger.info("in save(): success");
    }

    @Override
    public void deleteById(int id) {
        logger.info("in deletePlayerHittingDetails(): Calling REST API " + playerHittingDetailsRestUrl);

        // make REST call
        restTemplate.delete(playerHittingDetailsRestUrl + "/" + id);

        logger.info("in deletePlayerHittingDetails(): deleted playerHittingDetails id=" + id);
    }
}
