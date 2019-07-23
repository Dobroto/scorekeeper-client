package com.junak.scorekeeper.client.service.impl;

import com.junak.scorekeeper.client.model.Player;
import com.junak.scorekeeper.client.service.interfaces.PlayerService;
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
public class PlayerServiceRestClientImpl implements PlayerService {
    private RestTemplate restTemplate;

    private String scorekeeperRestUrl;
    private String playerRestUrl;

    private Logger logger = Logger.getLogger(getClass().getName());

    @Autowired
    public PlayerServiceRestClientImpl(RestTemplateBuilder builder,
                                       @Value("${scorekeeper.rest.url}") String theUrl) {
        restTemplate = builder.build();
        scorekeeperRestUrl = theUrl;
        playerRestUrl = scorekeeperRestUrl + "/players";
        logger.info("Loaded property: scorekeeper.rest.url=" + playerRestUrl);
    }

    @Override
    public List<Player> findAll() {
        logger.info("in findAll(): Calling REST API " + playerRestUrl);

        // make REST call
        ResponseEntity<List<Player>> responseEntity =
                restTemplate.exchange(playerRestUrl, HttpMethod.GET, null,
                        new ParameterizedTypeReference<List<Player>>() {});

        // get the list of players from response
        List<Player> players = responseEntity.getBody();

        logger.info("in findAll(): players" + players);

        return players;
    }

    @Override
    public List<Player> findAllTeamPlayers(int teamId) {
        return null;
    }

    @Override
    public Player findById(int id) {
        return null;
    }

    @Override
    public Player save(Player player) {
        return null;
    }

    @Override
    public void deleteById(int id) {

    }
}
