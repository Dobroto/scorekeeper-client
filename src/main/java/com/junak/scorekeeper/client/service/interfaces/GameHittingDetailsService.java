package com.junak.scorekeeper.client.service.interfaces;

import com.junak.scorekeeper.client.model.GameHittingDetails;
import java.util.List;

public interface GameHittingDetailsService {
    List<GameHittingDetails> findAll();

    GameHittingDetails findById(int id);

    void save(GameHittingDetails gameHittingDetails);

    void deleteById(int id);
}
