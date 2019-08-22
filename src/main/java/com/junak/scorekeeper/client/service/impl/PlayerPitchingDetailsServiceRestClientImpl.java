package com.junak.scorekeeper.client.service.impl;

import com.junak.scorekeeper.client.model.PlayerPitchingDetails;
import com.junak.scorekeeper.client.service.interfaces.PlayerPitchingDetailsService;
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
public class PlayerPitchingDetailsServiceRestClientImpl implements PlayerPitchingDetailsService {
    private RestTemplate restTemplate;
    private String scorekeeperRestUrl;
    private String playerPitchingDetailsRestUrl;

    private Logger logger = Logger.getLogger(getClass().getName());

    @Autowired
    public PlayerPitchingDetailsServiceRestClientImpl(RestTemplateBuilder builder,
                                            @Value("${scorekeeper.rest.url}") String theUrl) {
        restTemplate = builder.build();
        scorekeeperRestUrl = theUrl;
        playerPitchingDetailsRestUrl = scorekeeperRestUrl + "/playerPitchingDetails";
        logger.info("Loaded property: scorekeeper.rest.url=" + playerPitchingDetailsRestUrl);
    }

    @Override
    public List<PlayerPitchingDetails> findAll() {
        logger.info("in findAllPlayerPitchingDetailss(): Calling REST API " + playerPitchingDetailsRestUrl);

        // make REST call
        ResponseEntity<List<PlayerPitchingDetails>> responseEntity =
                restTemplate.exchange(playerPitchingDetailsRestUrl, HttpMethod.GET, null,
                        new ParameterizedTypeReference<List<PlayerPitchingDetails>>() {});

        // get the list of playerPitchingDetailss from response
        List<PlayerPitchingDetails> playerPitchingDetailss = responseEntity.getBody();

        logger.info("in findAllPlayerPitchingDetailss(): playerPitchingDetailss" + playerPitchingDetailss);

        return playerPitchingDetailss;
    }

    @Override
    public PlayerPitchingDetails findById(int id) {
        logger.info("in findById(): Calling REST API " + playerPitchingDetailsRestUrl + "/" + id);

        // make REST call
        PlayerPitchingDetails thePlayerPitchingDetails =
                restTemplate.getForObject(playerPitchingDetailsRestUrl + "/" + id, PlayerPitchingDetails.class);

        logger.info("in findById(): thePlayerPitchingDetails=" + thePlayerPitchingDetails);

        return thePlayerPitchingDetails;
    }

    @Override
    public void save(PlayerPitchingDetails playerPitchingDetails) {
        logger.info("in save(): Calling REST API " + playerPitchingDetailsRestUrl);

        int playerPitchingDetailsId = playerPitchingDetails.getId();

        // make REST call
        if (playerPitchingDetailsId == 0) {
            // add playerPitchingDetails
            restTemplate.postForEntity(playerPitchingDetailsRestUrl, playerPitchingDetails, String.class);

        } else {
            // update playerPitchingDetails
            restTemplate.put(playerPitchingDetailsRestUrl, playerPitchingDetails);
        }

        logger.info("in save(): success");
    }

    @Override
    public void deleteById(int id) {
        logger.info("in deletePlayerPitchingDetails(): Calling REST API " + playerPitchingDetailsRestUrl);

        // make REST call
        restTemplate.delete(playerPitchingDetailsRestUrl + "/" + id);

        logger.info("in deletePlayerPitchingDetails(): deleted playerPitchingDetails id=" + id);
    }
}
