package com.junak.scorekeeper.client.service.interfaces;

import com.junak.scorekeeper.client.model.Game;

import java.util.List;

public interface GameService {
    List<Game> findAll();

    Game findById(int id);

    void save(Game team);

    void deleteById(int id);
}
