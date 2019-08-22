package com.junak.scorekeeper.client.service.interfaces;

import com.junak.scorekeeper.client.model.GamePitchingDetails;

import java.util.List;

public interface GamePitchingDetailsService {
    List<GamePitchingDetails> findAll();

    GamePitchingDetails findById(int id);

    void save(GamePitchingDetails gamePitchingDetails);

    void deleteById(int id);
}
