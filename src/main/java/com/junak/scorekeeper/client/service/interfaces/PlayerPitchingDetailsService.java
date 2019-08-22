package com.junak.scorekeeper.client.service.interfaces;

import com.junak.scorekeeper.client.model.PlayerPitchingDetails;

import java.util.List;

public interface PlayerPitchingDetailsService {
    List<PlayerPitchingDetails> findAll();

    PlayerPitchingDetails findById(int id);

    void save(PlayerPitchingDetails playerPitchingDetails);

    void deleteById(int id);
}
