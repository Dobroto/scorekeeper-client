package com.junak.scorekeeper.client.model.wrapper;

import com.junak.scorekeeper.client.model.*;

import java.util.Date;
import java.util.List;

public class GameWrapper {
    private int id;

    private Date scheduledTime;

    private Date startTimeOfGame;

    private Date endTimeOfGame;

    private Team homeTeam;

    private Team visitorTeam;

    private FinalResult finalResult;

    private List<GameHittingDetails> gameHittingDetails;

    private List<GamePitchingDetails> gamePitchingDetails;

    private List<GameFieldingDetails> gameFieldingDetails;

    private Player winPitcher;

    private Player losePitcher;

    private Player savePitcher;

    private Player blownSavePitcher;

    private Player holdPitcher;

    private List<Inning> innings;

    private Inning currentInning;

    public GameWrapper() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getScheduledTime() {
        return scheduledTime;
    }

    public void setScheduledTime(Date scheduledTime) {
        this.scheduledTime = scheduledTime;
    }

    public Date getStartTimeOfGame() {
        return startTimeOfGame;
    }

    public void setStartTimeOfGame(Date startTimeOfGame) {
        this.startTimeOfGame = startTimeOfGame;
    }

    public Date getEndTimeOfGame() {
        return endTimeOfGame;
    }

    public void setEndTimeOfGame(Date endTimeOfGame) {
        this.endTimeOfGame = endTimeOfGame;
    }

    public Team getHomeTeam() {
        return homeTeam;
    }

    public void setHomeTeam(Team homeTeam) {
        this.homeTeam = homeTeam;
    }

    public Team getVisitorTeam() {
        return visitorTeam;
    }

    public void setVisitorTeam(Team visitorTeam) {
        this.visitorTeam = visitorTeam;
    }

    public FinalResult getFinalResult() {
        return finalResult;
    }

    public void setFinalResult(FinalResult finalResult) {
        this.finalResult = finalResult;
    }

    public List<GameHittingDetails> getGameHittingDetails() {
        return gameHittingDetails;
    }

    public void setGameHittingDetails(List<GameHittingDetails> gameHittingDetails) {
        this.gameHittingDetails = gameHittingDetails;
    }

    public List<GamePitchingDetails> getGamePitchingDetails() {
        return gamePitchingDetails;
    }

    public void setGamePitchingDetails(List<GamePitchingDetails> gamePitchingDetails) {
        this.gamePitchingDetails = gamePitchingDetails;
    }

    public List<GameFieldingDetails> getGameFieldingDetails() {
        return gameFieldingDetails;
    }

    public void setGameFieldingDetails(List<GameFieldingDetails> gameFieldingDetails) {
        this.gameFieldingDetails = gameFieldingDetails;
    }

    public Player getWinPitcher() {
        return winPitcher;
    }

    public void setWinPitcher(Player winPitcher) {
        this.winPitcher = winPitcher;
    }

    public Player getLosePitcher() {
        return losePitcher;
    }

    public void setLosePitcher(Player losePitcher) {
        this.losePitcher = losePitcher;
    }

    public Player getSavePitcher() {
        return savePitcher;
    }

    public void setSavePitcher(Player savePitcher) {
        this.savePitcher = savePitcher;
    }

    public Player getBlownSavePitcher() {
        return blownSavePitcher;
    }

    public void setBlownSavePitcher(Player blownSavePitcher) {
        this.blownSavePitcher = blownSavePitcher;
    }

    public Player getHoldPitcher() {
        return holdPitcher;
    }

    public void setHoldPitcher(Player holdPitcher) {
        this.holdPitcher = holdPitcher;
    }

    public List<Inning> getInnings() {
        return innings;
    }

    public void setInnings(List<Inning> innings) {
        this.innings = innings;
    }

    public Inning getCurrentInning() {
        return currentInning;
    }

    public void setCurrentInning(Inning currentInning) {
        this.currentInning = currentInning;
    }
}
