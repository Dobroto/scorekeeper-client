package com.junak.scorekeeper.client.service.interfaces;

import com.junak.scorekeeper.client.model.Game;

import java.util.List;

public interface GameService {
    List<Game> findAll();

    Game findById(int id);

    void save(Game team);

    void deleteGame(int id);

    void createNewGame(int homeTeamId, int visitorTeamId);

    void startGame(int gameId);

    void ball(int gameId, int pitcherId, int batterId);

    void strike(int pitcherId, int batterId);

    void strikeoutLooking(int gameId, int pitcherId, int batterId);

    void strikeoutSwinging(int gameId, int pitcherId, int batterId);

    void caughtFoulTip(int gameId, int pitcherId, int batterId);

    void buntFoul(int gameId, int pitcherId, int batterId);

    void foul(int gameId, int batterId);

    void walk(int gameId, int pitcherId, int batterId);

    void intentionalWalk(int gameId, int pitcherId, int batterId);

    void dropped3rdStrikeOut(int gameId, int pitcherId, int batterId);

    void dropped3rdStrikeSafe(int gameId, int pitcherId, int batterId);

    void wildPitch3rdStrike(int gameId, int pitcherId, int batterId);

    void caughtStealing(int gameId, int pitcherId, int runnerId, int catcherId, int basemanId);

    void stolenBase(int gameId, int pitcherId, int runnerId, String baseStolen);

    void hitSingle(int gameId, int pitcherId, int batterId);

    void hitDouble(int gameId, int pitcherId, int batterId);

    void hitTriple(int gameId, int pitcherId, int batterId);

    void homeRun(int gameId, int pitcherId, int batterId);

    void inParkHomeRun(int gameId, int pitcherId, int batterId);

    void buntSafe(int gameId, int pitcherId, int batterId);

    void buntOut(int gameId, int pitcherId, int batterId);

    void error(int gameId, int pitcherId, int batterId);

    void hitByPitch(int gameId, int pitcherId, int batterId);

    void fieldersChoice(int gameId, int pitcherId, int batterId);

    void groundOut(int gameId, int pitcherId, int batterId);

    void lineDrive(int gameId, int pitcherId, int batterId);

    void flyOut(int gameId, int pitcherId, int batterId);

    void sacrificeFly(int gameId, int pitcherId, int batterId);

    void sacrificeBunt(int gameId, int pitcherId, int batterId);

    void infieldFly(int gameId, int pitcherId, int batterId);

    void hitByBall(int gameId, int pitcherId, int batterId);

    void runnerInterference(int gameId, int pitcherId, int batterId);

    void offensiveInterference(int gameId, int pitcherId, int batterId);
}
