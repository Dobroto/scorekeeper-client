package com.junak.scorekeeper.client.service.interfaces;

import com.junak.scorekeeper.client.model.PlayerFieldingDetails;

import java.util.List;

public interface PlayerFieldingDetailsService {
    List<PlayerFieldingDetails> findAll();

    PlayerFieldingDetails findById(int id);

    void save(PlayerFieldingDetails playerFieldingDetails);

    void deleteById(int id);
}
