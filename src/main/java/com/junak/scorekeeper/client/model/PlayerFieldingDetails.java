package com.junak.scorekeeper.client.model;

public class PlayerFieldingDetails {
    private int id;

    private int gamesStarted;

    private int gamesPlayed;

    private double innings;

    private int totalChances;

    private int putOut;

    private int assists;

    private int errors;

    private int doublePlays;

    private Double averageOfErrorsPerTotalChances;

    private int player;

    public PlayerFieldingDetails() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getGamesStarted() {
        return gamesStarted;
    }

    public void setGamesStarted(int gamesStarted) {
        this.gamesStarted = gamesStarted;
    }

    public int getGamesPlayed() {
        return gamesPlayed;
    }

    public void setGamesPlayed(int gamesPlayed) {
        this.gamesPlayed = gamesPlayed;
    }

    public double getInnings() {
        return innings;
    }

    public void setInnings(double innings) {
        this.innings = innings;
    }

    public int getTotalChances() {
        return totalChances;
    }

    public void setTotalChances(int totalChances) {
        this.totalChances = totalChances;
    }

    public int getPutOut() {
        return putOut;
    }

    public void setPutOut(int putOut) {
        this.putOut = putOut;
    }

    public int getAssists() {
        return assists;
    }

    public void setAssists(int assists) {
        this.assists = assists;
    }

    public int getErrors() {
        return errors;
    }

    public void setErrors(int errors) {
        this.errors = errors;
    }

    public int getDoublePlays() {
        return doublePlays;
    }

    public void setDoublePlays(int doublePlays) {
        this.doublePlays = doublePlays;
    }

    public Double getAverageOfErrorsPerTotalChances() {
        return averageOfErrorsPerTotalChances;
    }

    public void setAverageOfErrorsPerTotalChances(Double averageOfErrorsPerTotalChances) {
        this.averageOfErrorsPerTotalChances = averageOfErrorsPerTotalChances;
    }

    public int getPlayer() {
        return player;
    }

    public void setPlayer(int player) {
        this.player = player;
    }
}
