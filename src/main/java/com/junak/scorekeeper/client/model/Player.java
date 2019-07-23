package com.junak.scorekeeper.client.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Player {
    private int id;

    private String firstName;

    private String lastName;

    private int jerseyNumber;

    private boolean starter;

    private String defencePosition;

    private String offencePosition;

    private int battingOrder;

    private int ballCount;

    private int strikeCount;

    private int playerHittingDetails;

    private int playerPitchingDetails;

    private int playerFieldingDetails;

    private int team;

    private List<Integer> winPitcherGames;

    private List<Integer> losePitcherGames;

    private List<Integer> savePitcherGames;

    private List<Integer> blownSavePitcherGames;

    private List<Integer> holdPitcherGames;

    private List<Integer> gameHittingDetails;

    private List<Integer> gameFieldingDetails;

    private List<Integer> gamePitchingDetails;

    public Player() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getJerseyNumber() {
        return jerseyNumber;
    }

    public void setJerseyNumber(int jerseyNumber) {
        this.jerseyNumber = jerseyNumber;
    }

    public boolean isStarter() {
        return starter;
    }

    public void setStarter(boolean starter) {
        this.starter = starter;
    }

    public String getDefencePosition() {
        return defencePosition;
    }

    public void setDefencePosition(String defencePosition) {
        this.defencePosition = defencePosition;
    }

    public String getOffencePosition() {
        return offencePosition;
    }

    public void setOffencePosition(String offencePosition) {
        this.offencePosition = offencePosition;
    }

    public int getBattingOrder() {
        return battingOrder;
    }

    public void setBattingOrder(int battingOrder) {
        this.battingOrder = battingOrder;
    }

    public int getBallCount() {
        return ballCount;
    }

    public void setBallCount(int ballCount) {
        this.ballCount = ballCount;
    }

    public int getStrikeCount() {
        return strikeCount;
    }

    public void setStrikeCount(int strikeCount) {
        this.strikeCount = strikeCount;
    }

    public int getPlayerHittingDetails() {
        return playerHittingDetails;
    }

    public void setPlayerHittingDetails(int playerHittingDetails) {
        this.playerHittingDetails = playerHittingDetails;
    }

    public int getPlayerPitchingDetails() {
        return playerPitchingDetails;
    }

    public void setPlayerPitchingDetails(int playerPitchingDetails) {
        this.playerPitchingDetails = playerPitchingDetails;
    }

    public int getPlayerFieldingDetails() {
        return playerFieldingDetails;
    }

    public void setPlayerFieldingDetails(int playerFieldingDetails) {
        this.playerFieldingDetails = playerFieldingDetails;
    }

    public int getTeam() {
        return team;
    }

    public void setTeam(int team) {
        this.team = team;
    }

    public List<Integer> getWinPitcherGames() {
        return winPitcherGames;
    }

    public void setWinPitcherGames(List<Integer> winPitcherGames) {
        this.winPitcherGames = winPitcherGames;
    }

    public List<Integer> getLosePitcherGames() {
        return losePitcherGames;
    }

    public void setLosePitcherGames(List<Integer> losePitcherGames) {
        this.losePitcherGames = losePitcherGames;
    }

    public List<Integer> getSavePitcherGames() {
        return savePitcherGames;
    }

    public void setSavePitcherGames(List<Integer> savePitcherGames) {
        this.savePitcherGames = savePitcherGames;
    }

    public List<Integer> getBlownSavePitcherGames() {
        return blownSavePitcherGames;
    }

    public void setBlownSavePitcherGames(List<Integer> blownSavePitcherGames) {
        this.blownSavePitcherGames = blownSavePitcherGames;
    }

    public List<Integer> getHoldPitcherGames() {
        return holdPitcherGames;
    }

    public void setHoldPitcherGames(List<Integer> holdPitcherGames) {
        this.holdPitcherGames = holdPitcherGames;
    }

    public List<Integer> getGameHittingDetails() {
        return gameHittingDetails;
    }

    public void setGameHittingDetails(List<Integer> gameHittingDetails) {
        this.gameHittingDetails = gameHittingDetails;
    }

    public List<Integer> getGameFieldingDetails() {
        return gameFieldingDetails;
    }

    public void setGameFieldingDetails(List<Integer> gameFieldingDetails) {
        this.gameFieldingDetails = gameFieldingDetails;
    }

    public List<Integer> getGamePitchingDetails() {
        return gamePitchingDetails;
    }

    public void setGamePitchingDetails(List<Integer> gamePitchingDetails) {
        this.gamePitchingDetails = gamePitchingDetails;
    }
}
