package com.junak.scorekeeper.client.service.impl;

import com.junak.scorekeeper.client.model.Game;
import com.junak.scorekeeper.client.model.Inning;
import com.junak.scorekeeper.client.service.interfaces.InningService;
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
public class InningServiceRestClientImpl implements InningService {
    private RestTemplate restTemplate;
    private String scorekeeperRestUrl;
    private String inningRestUrl;

    private Logger logger = Logger.getLogger(getClass().getName());

    @Autowired
    public InningServiceRestClientImpl(RestTemplateBuilder builder,
                                            @Value("${scorekeeper.rest.url}") String theUrl) {
        restTemplate = builder.build();
        scorekeeperRestUrl = theUrl;
        inningRestUrl = scorekeeperRestUrl + "/innings";
        logger.info("Loaded property: scorekeeper.rest.url=" + inningRestUrl);
    }

    @Override
    public List<Inning> findAll() {
        logger.info("in findAllInnings(): Calling REST API " + inningRestUrl);

        // make REST call
        ResponseEntity<List<Inning>> responseEntity =
                restTemplate.exchange(inningRestUrl, HttpMethod.GET, null,
                        new ParameterizedTypeReference<List<Inning>>() {});

        // get the list of innings from response
        List<Inning> innings = responseEntity.getBody();

        logger.info("in findAllInnings(): innings" + innings);

        return innings;
    }

    @Override
    public Inning findById(int id) {
        logger.info("in findById(): Calling REST API " + inningRestUrl + "/" + id);

        // make REST call
        Inning theInning =
                restTemplate.getForObject(inningRestUrl + "/" + id, Inning.class);

        logger.info("in findById(): theInning=" + theInning);

        return theInning;
    }

    @Override
    public void save(Inning inning) {
        logger.info("in save(): Calling REST API " + inningRestUrl);

        int inningId = inning.getId();

        // make REST call
        if (inningId == 0) {
            // add inning
            restTemplate.postForEntity(inningRestUrl, inning, String.class);

        } else {
            // update inning
            restTemplate.put(inningRestUrl, inning);
        }

        logger.info("in save(): success");
    }

    @Override
    public void deleteById(int id) {
        logger.info("in deleteInning(): Calling REST API " + inningRestUrl);

        // make REST call
        restTemplate.delete(inningRestUrl + "/" + id);

        logger.info("in deleteInning(): deleted inning id=" + id);
    }

    @Override
    public List<Inning> getInningsList(Game game) {
        String getInningsListUrl = inningRestUrl + "/game/list/" + game.getId();

        logger.info("in getInningsList(): Calling REST API " + getInningsListUrl);

        // make REST call
        ResponseEntity<List<Inning>> responseEntity =
                restTemplate.exchange(getInningsListUrl, HttpMethod.GET, null,
                        new ParameterizedTypeReference<List<Inning>>() {});

        // get the list of innings from response
        List<Inning> innings = responseEntity.getBody();

        logger.info("in getInningsList(): innings" + innings);

        return innings;
    }

    @Override
    public Inning getCurrentInning(Game game) {
        String getCurrentInningUrl = inningRestUrl + "/game/" + game.getId();
        logger.info("in getCurrentInning(): Calling REST API " + getCurrentInningUrl);

        // make REST call
        Inning theInning =
                restTemplate.getForObject(getCurrentInningUrl, Inning.class);

        logger.info("in getCurrentInning(): theInning=" + theInning);

        return theInning;
    }
}
