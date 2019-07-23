package com.junak.scorekeeper.client.service.interfaces;

import com.junak.scorekeeper.client.model.Player;

import java.util.List;

public interface PlayerService {
    List<Player> findAll();

    List<Player> findAllTeamPlayers(int teamId);

    Player findById(int id);

    Player save(Player player);

    void deleteById(int id);
}
