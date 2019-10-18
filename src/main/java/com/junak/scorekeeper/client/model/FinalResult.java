package com.junak.scorekeeper.client.model;

public class FinalResult {
    private int id;

    private int visitorTeamScore;

    private int homeTeamScore;

    private int visitorTeamHits;

    private int homeTeamHits;

    private int visitorTeamErrors;

    private int homeTeamErrors;

    private int game;

    public FinalResult() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getVisitorTeamScore() {
        return visitorTeamScore;
    }

    public void setVisitorTeamScore(int visitorTeamScore) {
        this.visitorTeamScore = visitorTeamScore;
    }

    public int getHomeTeamScore() {
        return homeTeamScore;
    }

    public void setHomeTeamScore(int homeTeamScore) {
        this.homeTeamScore = homeTeamScore;
    }

    public int getVisitorTeamHits() {
        return visitorTeamHits;
    }

    public void setVisitorTeamHits(int visitorTeamHits) {
        this.visitorTeamHits = visitorTeamHits;
    }

    public int getHomeTeamHits() {
        return homeTeamHits;
    }

    public void setHomeTeamHits(int homeTeamHits) {
        this.homeTeamHits = homeTeamHits;
    }

    public int getVisitorTeamErrors() {
        return visitorTeamErrors;
    }

    public void setVisitorTeamErrors(int visitorTeamErrors) {
        this.visitorTeamErrors = visitorTeamErrors;
    }

    public int getHomeTeamErrors() {
        return homeTeamErrors;
    }

    public void setHomeTeamErrors(int homeTeamErrors) {
        this.homeTeamErrors = homeTeamErrors;
    }

    public int getGame() {
        return game;
    }

    public void setGame(int game) {
        this.game = game;
    }

    @Override
    public String toString() {
        return "FinalResult{" +
                "id=" + id +
                ", visitorTeamScore=" + visitorTeamScore +
                ", homeTeamScore=" + homeTeamScore +
                ", game=" + game +
                '}';
    }
}
