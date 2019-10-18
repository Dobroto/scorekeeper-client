package com.junak.scorekeeper.client.service.impl;

import com.junak.scorekeeper.client.model.PlayerFieldingDetails;
import com.junak.scorekeeper.client.service.interfaces.PlayerFieldingDetailsService;
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
public class PlayerFieldingDetailsServiceRestClientImpl implements PlayerFieldingDetailsService {
    private RestTemplate restTemplate;
    private String scorekeeperRestUrl;
    private String playerFieldingDetailsRestUrl;

    private Logger logger = Logger.getLogger(getClass().getName());

    @Autowired
    public PlayerFieldingDetailsServiceRestClientImpl(RestTemplateBuilder builder,
                                            @Value("${scorekeeper.rest.url}") String theUrl) {
        restTemplate = builder.build();
        scorekeeperRestUrl = theUrl;
        playerFieldingDetailsRestUrl = scorekeeperRestUrl + "/playerFieldingDetails";
        logger.info("Loaded property: scorekeeper.rest.url=" + playerFieldingDetailsRestUrl);
    }

    @Override
    public List<PlayerFieldingDetails> findAll() {
        logger.info("in findAllPlayerFieldingDetailss(): Calling REST API " + playerFieldingDetailsRestUrl);

        // make REST call
        ResponseEntity<List<PlayerFieldingDetails>> responseEntity =
                restTemplate.exchange(playerFieldingDetailsRestUrl, HttpMethod.GET, null,
                        new ParameterizedTypeReference<List<PlayerFieldingDetails>>() {});

        // get the list of playerFieldingDetailss from response
        List<PlayerFieldingDetails> playerFieldingDetailss = responseEntity.getBody();

        logger.info("in findAllPlayerFieldingDetailss(): playerFieldingDetailss" + playerFieldingDetailss);

        return playerFieldingDetailss;
    }

    @Override
    public PlayerFieldingDetails findById(int id) {
        logger.info("in findById(): Calling REST API " + playerFieldingDetailsRestUrl + "/" + id);

        // make REST call
        PlayerFieldingDetails thePlayerFieldingDetails =
                restTemplate.getForObject(playerFieldingDetailsRestUrl + "/" + id, PlayerFieldingDetails.class);

        logger.info("in findById(): thePlayerFieldingDetails=" + thePlayerFieldingDetails);

        return thePlayerFieldingDetails;
    }

    @Override
    public void save(PlayerFieldingDetails playerFieldingDetails) {
        logger.info("in save(): Calling REST API " + playerFieldingDetailsRestUrl);

        int playerFieldingDetailsId = playerFieldingDetails.getId();

        // make REST call
        if (playerFieldingDetailsId == 0) {
            // add playerFieldingDetails
            restTemplate.postForEntity(playerFieldingDetailsRestUrl, playerFieldingDetails, String.class);

        } else {
            // update playerFieldingDetails
            restTemplate.put(playerFieldingDetailsRestUrl, playerFieldingDetails);
        }

        logger.info("in save(): success");
    }

    @Override
    public void deleteById(int id) {
        logger.info("in deletePlayerFieldingDetails(): Calling REST API " + playerFieldingDetailsRestUrl);

        // make REST call
        restTemplate.delete(playerFieldingDetailsRestUrl + "/" + id);

        logger.info("in deletePlayerFieldingDetails(): deleted playerFieldingDetails id=" + id);
    }
}
