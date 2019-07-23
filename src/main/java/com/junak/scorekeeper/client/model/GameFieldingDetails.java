package com.junak.scorekeeper.client.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GameFieldingDetails {
    private int id;

    private double innings;

    private int totalChances;

    private int putOut;

    private int assists;

    private int errors;

    private int doublePlays;

    private Double averageOfErrorsPerTotalChances;

    private Player player;

    private Game game;

    public GameFieldingDetails() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }
}
