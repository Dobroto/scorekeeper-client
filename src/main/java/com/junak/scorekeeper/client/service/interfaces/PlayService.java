package com.junak.scorekeeper.client.service.interfaces;

import com.junak.scorekeeper.client.model.Play;

import java.util.List;

public interface PlayService {

    List<Play> getAllPlaysInGame(int gameId);
}
