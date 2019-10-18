package com.junak.scorekeeper.client.service.impl;

import com.junak.scorekeeper.client.model.FinalResult;
import com.junak.scorekeeper.client.service.interfaces.FinalResultService;
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
public class FinalResultServiceRestClientImpl implements FinalResultService {
    private RestTemplate restTemplate;
    private String scorekeeperRestUrl;
    private String finalResultRestUrl;

    private Logger logger = Logger.getLogger(getClass().getName());

    @Autowired
    public FinalResultServiceRestClientImpl(RestTemplateBuilder builder,
                                            @Value("${scorekeeper.rest.url}") String theUrl) {
        restTemplate = builder.build();
        scorekeeperRestUrl = theUrl;
        finalResultRestUrl = scorekeeperRestUrl + "/finalResults";
        logger.info("Loaded property: scorekeeper.rest.url=" + finalResultRestUrl);
    }

    @Override
    public List<FinalResult> findAll() {
        logger.info("in findAllFinalResults(): Calling REST API " + finalResultRestUrl);

        // make REST call
        ResponseEntity<List<FinalResult>> responseEntity =
                restTemplate.exchange(finalResultRestUrl, HttpMethod.GET, null,
                        new ParameterizedTypeReference<List<FinalResult>>() {});

        // get the list of finalResults from response
        List<FinalResult> finalResults = responseEntity.getBody();

        logger.info("in findAllFinalResults(): finalResults" + finalResults);

        return finalResults;
    }

    @Override
    public FinalResult findById(int id) {
        logger.info("in findById(): Calling REST API " + finalResultRestUrl + "/" + id);

        // make REST call
        FinalResult theFinalResult =
                restTemplate.getForObject(finalResultRestUrl + "/" + id, FinalResult.class);

        logger.info("in findById(): theFinalResult=" + theFinalResult);

        return theFinalResult;
    }

    @Override
    public void save(FinalResult finalResult) {
        logger.info("in save(): Calling REST API " + finalResultRestUrl);

        int finalResultId = finalResult.getId();

        // make REST call
        if (finalResultId == 0) {
            // add finalResult
            restTemplate.postForEntity(finalResultRestUrl, finalResult, String.class);

        } else {
            // update finalResult
            restTemplate.put(finalResultRestUrl, finalResult);
        }

        logger.info("in save(): success");
    }

    @Override
    public void deleteById(int id) {
        logger.info("in deleteFinalResult(): Calling REST API " + finalResultRestUrl);

        // make REST call
        restTemplate.delete(finalResultRestUrl + "/" + id);

        logger.info("in deleteFinalResult(): deleted finalResult id=" + id);
    }
}
