package com.junak.scorekeeper.client.service.interfaces;

import com.junak.scorekeeper.client.model.Game;
import com.junak.scorekeeper.client.model.Inning;

import java.util.List;

public interface InningService {
    List<Inning> findAll();

    Inning findById(int id);

    void save(Inning inning);

    void deleteById(int id);
}
