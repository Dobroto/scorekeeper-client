package com.junak.scorekeeper.client.model;

import java.util.Date;
import java.util.List;

public class Game {

    private int id;

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

    private String lastCommand;

    private List<Inning> innings;

    public Game() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getLastCommand() {
        return lastCommand;
    }

    public void setLastCommand(String lastCommand) {
        this.lastCommand = lastCommand;
    }

    public List<Inning> getInnings() {
        return innings;
    }

    public void setInnings(List<Inning> innings) {
        this.innings = innings;
    }

    @Override
    public String toString() {
        return "Game{" +
                "id=" + id +
                ", startTimeOfGame=" + startTimeOfGame +
                ", endTimeOfGame=" + endTimeOfGame +
                ", homeTeam=" + homeTeam +
                ", visitorTeam=" + visitorTeam +
                ", finalResult=" + finalResult +
                ", gameHittingDetails=" + gameHittingDetails +
                ", gamePitchingDetails=" + gamePitchingDetails +
                ", gameFieldingDetails=" + gameFieldingDetails +
                ", winPitcher=" + winPitcher +
                ", losePitcher=" + losePitcher +
                ", savePitcher=" + savePitcher +
                ", blownSavePitcher=" + blownSavePitcher +
                ", holdPitcher=" + holdPitcher +
                ", lastCommand='" + lastCommand + '\'' +
                ", innings=" + innings +
                '}';
    }
}
