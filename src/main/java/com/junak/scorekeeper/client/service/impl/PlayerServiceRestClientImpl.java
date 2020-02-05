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
    private String teamPlayersRestUrl;

    private Logger logger = Logger.getLogger(getClass().getName());

    @Autowired
    public PlayerServiceRestClientImpl(RestTemplateBuilder builder,
                                       @Value("${scorekeeper.rest.url}") String theUrl) {
        restTemplate = builder.build();
        scorekeeperRestUrl = theUrl;
        playerRestUrl = scorekeeperRestUrl + "/players";
        teamPlayersRestUrl = playerRestUrl + "/team/";
        logger.info("Loaded property: scorekeeper.rest.url=" + playerRestUrl);
    }

    @Override
    public List<Player> findAll() {
        logger.info("in findAllPlayers(): Calling REST API " + playerRestUrl);

        // make REST call
        ResponseEntity<List<Player>> responseEntity =
                restTemplate.exchange(playerRestUrl, HttpMethod.GET, null,
                        new ParameterizedTypeReference<List<Player>>() {});

        // get the list of players from response

        List<Player> players = responseEntity.getBody();

        logger.info("in findAllPlayers(): players" + players);

        return players;
    }

    @Override
    public List<Player> findAllTeamPlayers(int teamId) {
        logger.info("in findAllTeamPlayers(): Calling REST API " + teamPlayersRestUrl + teamId);

        // make REST call
        ResponseEntity<List<Player>> responseEntity =
                restTemplate.exchange(teamPlayersRestUrl + teamId, HttpMethod.GET, null,
                        new ParameterizedTypeReference<List<Player>>() {});

        // get the list of players from response
        List<Player> players = responseEntity.getBody();

        logger.info("in findAllTeamPlayers(): players" + players);

        return players;
    }

    @Override
    public Player findById(int id) {
        logger.info("in findById(): Calling REST API " + playerRestUrl + "/" + id);

        // make REST call
        Player thePlayer =
                restTemplate.getForObject(playerRestUrl + "/" + id, Player.class);

        logger.info("in findById(): thePlayer=" + thePlayer);

        return thePlayer;
    }

    @Override
    public void save(Player player) {
        logger.info("in save(): Calling REST API " + playerRestUrl);

        int playerId = player.getId();

        // make REST call
        if (playerId == 0) {
            // add player
            restTemplate.postForEntity(playerRestUrl, player, String.class);

        } else {
            // update player
            restTemplate.put(playerRestUrl, player);
        }

        logger.info("in save(): success");
    }

    @Override
    public void deleteById(int id) {
        logger.info("in deletePlayer(): Calling REST API " + playerRestUrl);

        // make REST call
        restTemplate.delete(playerRestUrl + "/" + id);

        logger.info("in deletePlayer(): deleted player id=" + id);
    }
}
