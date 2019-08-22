package com.junak.scorekeeper.client.model;

public class FinalResult {
    private int id;

    private int visitorTeamScore;

    private int homeTeamScore;

    private Game game;

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

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
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
