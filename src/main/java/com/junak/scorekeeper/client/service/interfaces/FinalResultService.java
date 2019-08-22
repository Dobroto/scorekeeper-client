package com.junak.scorekeeper.client.service.interfaces;

import com.junak.scorekeeper.client.model.FinalResult;

import java.util.List;

public interface FinalResultService {
    List<FinalResult> findAll();

    FinalResult findById(int id);

    void save(FinalResult finalResult);

    void deleteById(int id);
}
