package com.junak.scorekeeper.client.service.interfaces;

import com.junak.scorekeeper.client.model.PlayerHittingDetails;

import java.util.List;

public interface PlayerHittingDetailsService {
    List<PlayerHittingDetails> findAll();

    PlayerHittingDetails findById(int id);

    void save(PlayerHittingDetails playerHittingDetails);

    void deleteById(int id);
}
