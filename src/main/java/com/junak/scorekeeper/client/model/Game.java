package com.junak.scorekeeper.client.model;

import java.util.Date;
import java.util.List;

public class Game {

    private int id;

    private Date scheduledTime;

    private Date startTimeOfGame;

    private Date endTimeOfGame;

    private int homeTeam;

    private int visitorTeam;

    private int finalResult;

    private List<Integer> gameHittingDetails;

    private List<Integer> gamePitchingDetails;

    private List<Integer> gameFieldingDetails;

    private int winPitcher;

    private int losePitcher;

    private int savePitcher;

    private int blownSavePitcher;

    private int holdPitcher;

    private List<Integer> innings;

    public Game() {

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

    public int getHomeTeam() {
        return homeTeam;
    }

    public void setHomeTeam(int homeTeam) {
        this.homeTeam = homeTeam;
    }

    public int getVisitorTeam() {
        return visitorTeam;
    }

    public void setVisitorTeam(int visitorTeam) {
        this.visitorTeam = visitorTeam;
    }

    public int getFinalResult() {
        return finalResult;
    }

    public void setFinalResult(int finalResult) {
        this.finalResult = finalResult;
    }

    public List<Integer> getGameHittingDetails() {
        return gameHittingDetails;
    }

    public void setGameHittingDetails(List<Integer> gameHittingDetails) {
        this.gameHittingDetails = gameHittingDetails;
    }

    public List<Integer> getGamePitchingDetails() {
        return gamePitchingDetails;
    }

    public void setGamePitchingDetails(List<Integer> gamePitchingDetails) {
        this.gamePitchingDetails = gamePitchingDetails;
    }

    public List<Integer> getGameFieldingDetails() {
        return gameFieldingDetails;
    }

    public void setGameFieldingDetails(List<Integer> gameFieldingDetails) {
        this.gameFieldingDetails = gameFieldingDetails;
    }

    public int getWinPitcher() {
        return winPitcher;
    }

    public void setWinPitcher(int winPitcher) {
        this.winPitcher = winPitcher;
    }

    public int getLosePitcher() {
        return losePitcher;
    }

    public void setLosePitcher(int losePitcher) {
        this.losePitcher = losePitcher;
    }

    public int getSavePitcher() {
        return savePitcher;
    }

    public void setSavePitcher(int savePitcher) {
        this.savePitcher = savePitcher;
    }

    public int getBlownSavePitcher() {
        return blownSavePitcher;
    }

    public void setBlownSavePitcher(int blownSavePitcher) {
        this.blownSavePitcher = blownSavePitcher;
    }

    public int getHoldPitcher() {
        return holdPitcher;
    }

    public void setHoldPitcher(int holdPitcher) {
        this.holdPitcher = holdPitcher;
    }

    public List<Integer> getInnings() {
        return innings;
    }

    public void setInnings(List<Integer> innings) {
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
                ", innings=" + innings +
                '}';
    }
}
