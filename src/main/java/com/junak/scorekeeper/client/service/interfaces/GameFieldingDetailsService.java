package com.junak.scorekeeper.client.service.interfaces;

import com.junak.scorekeeper.client.model.Game;
import com.junak.scorekeeper.client.model.GameFieldingDetails;
import com.junak.scorekeeper.client.model.Player;

import java.util.List;

public interface GameFieldingDetailsService {
    List<GameFieldingDetails> findAll();

    GameFieldingDetails findById(int id);

    void save(GameFieldingDetails gameFieldingDetails);

    void deleteById(int id);

    GameFieldingDetails getGameFieldingDetails(Player player, Game game);
}
