package com.junak.scorekeeper.client.service.impl;

import com.junak.scorekeeper.client.model.Team;
import com.junak.scorekeeper.client.service.interfaces.TeamService;
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
public class TeamServiceRestClientImpl implements TeamService {
    private RestTemplate restTemplate;
    private String scorekeeperRestUrl;
    private String teamRestUrl;

    private Logger logger = Logger.getLogger(getClass().getName());

    @Autowired
    public TeamServiceRestClientImpl(RestTemplateBuilder builder,
                                            @Value("${scorekeeper.rest.url}") String theUrl) {
        restTemplate = builder.build();
        scorekeeperRestUrl = theUrl;
        teamRestUrl = scorekeeperRestUrl + "/teams";
        logger.info("Loaded property: scorekeeper.rest.url=" + teamRestUrl);
    }

    @Override
    public List<Team> findAll() {
        logger.info("in findAllTeams(): Calling REST API " + teamRestUrl);

        // make REST call
        ResponseEntity<List<Team>> responseEntity =
                restTemplate.exchange(teamRestUrl, HttpMethod.GET, null,
                        new ParameterizedTypeReference<List<Team>>() {});

        // get the list of teams from response
        List<Team> teams = responseEntity.getBody();

        logger.info("in findAllTeams(): teams" + teams);

        return teams;
    }

    @Override
    public Team findById(int id) {
        logger.info("in findById(): Calling REST API " + teamRestUrl + "/" + id);

        // make REST call
        Team theTeam =
                restTemplate.getForObject(teamRestUrl + "/" + id, Team.class);

        logger.info("in findById(): theTeam=" + theTeam);

        return theTeam;
    }

    @Override
    public void save(Team team) {
        logger.info("in save(): Calling REST API " + teamRestUrl);

        int teamId = team.getId();

        // make REST call
        if (teamId == 0) {
            // add team
            restTemplate.postForEntity(teamRestUrl, team, String.class);

        } else {
            // update team
            restTemplate.put(teamRestUrl, team);
        }

        logger.info("in save(): success");
    }

    @Override
    public void deleteById(int id) {
        logger.info("in deleteTeam(): Calling REST API " + teamRestUrl);

        // make REST call
        restTemplate.delete(teamRestUrl + "/" + id);

        logger.info("in deleteTeam(): deleted team id=" + id);
    }
}
