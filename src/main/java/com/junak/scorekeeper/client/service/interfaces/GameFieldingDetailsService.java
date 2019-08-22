package com.junak.scorekeeper.client.service.interfaces;

import com.junak.scorekeeper.client.model.GameFieldingDetails;

import java.util.List;

public interface GameFieldingDetailsService {
    List<GameFieldingDetails> findAll();

    GameFieldingDetails findById(int id);

    void save(GameFieldingDetails gameFieldingDetails);

    void deleteById(int id);
}
