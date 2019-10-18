package com.junak.scorekeeper.client.service.interfaces;

import com.junak.scorekeeper.client.model.Team;

import java.util.List;

public interface TeamService {
    List<Team> findAll();

    Team findById(int id);

    void save(Team team);

    void deleteById(int id);
}
